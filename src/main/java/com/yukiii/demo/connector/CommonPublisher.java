package com.yukiii.demo.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.entity.EventStore;
import com.yukiii.demo.exception.DemoException;
import com.yukiii.demo.repository.EventStoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component("commonPublisher")
public class CommonPublisher {

  @Autowired
  ObjectMapper mapper;

  @Autowired
  private TaskExecutor taskExecutor;

  @Autowired
  EventStoreRepository eventStoreRepository;

  public void send(
    RabbitTemplate rabbitTemplate,
    Object value,
    String idempotenceKey,
    String exchangeName,
    String routingKey
  ) {
    String payload = buildPayload(value);

    var event = EventStore
      .builder()
      .key(UUID.fromString(idempotenceKey))
      .exchange(exchangeName)
      .routingKey(routingKey)
      .message(payload)
      .build();

    var createdEvent = eventStoreRepository.save(event);
    try {
      send(rabbitTemplate, payload, idempotenceKey, exchangeName, routingKey);
      updatePublishedMessage(createdEvent);
    }catch(AmqpException ex){
      throw new DemoException(AppConstant.ResponseConstant.DATA_PUBLISHFAIL);
    }
  }

  public void send(
    RabbitTemplate rabbitTemplate,
    String payload,
    String idempotenceKey,
    String exchangeName,
    String routingKey
  ) throws AmqpException {
    MessageProperties properties = new MessageProperties();
    properties.setHeader(AppConstant.HEADER_X_IDEMPOTENCE_KEY, idempotenceKey);
    Message message = new Message(payload.getBytes(), properties);

    rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    log.info("Sending message: {}", payload);
  }

  private String buildPayload(Object value){
    String payload = "";
    try {
      payload = mapper.writeValueAsString(value);
    }catch(JsonProcessingException e){
      throw new DemoException(AppConstant.ResponseConstant.DATA_PARSEBODYERROR);
    }

    return payload;
  }

  public void updatePublishedMessage(EventStore eventStore){
    eventStore.setPublishedAt(LocalDateTime.now());
    eventStoreRepository.save(eventStore);
  }

  public void republishUnsentMessage(
    RabbitTemplate rabbitTemplate,
    String exchangeName,
    String routingKey
  ) {
    var events = eventStoreRepository.findAllUnpublished(exchangeName, routingKey);
    LocalDateTime currentTime = LocalDateTime.now();

    for(EventStore eventStore : events){
      Duration duration = Duration.between(eventStore.getCreatedAt(), currentTime);
      if(duration.toSeconds() > AppConstant.SECONDS_REPUBLISH_MESSAGE){
        taskExecutor.execute(new PublishRunner(this, eventStore, rabbitTemplate));
      }
    }
  }
}
