package com.yukiii.demo.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.dto.demo.DemoReadDto;
import com.yukiii.demo.exception.DemoException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component("demoListener")
public class DemoListener {

  @Autowired
  @Qualifier("rabbitTemplate")
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private CommonListener commonListener;

  @Setter
  @Value(AppConstant.PREFIX_MQ_QUEUE + "${connector.demo.send-data.queue}" + AppConstant.SUFFIX_MQ_WORK)
  private String queueName;

  @Setter
  @Value(AppConstant.PREFIX_MQ_QUEUE + "${connector.demo.send-data.queue}" + AppConstant.SUFFIX_MQ_DL_QUEUE)
  private String dlQueueName;

  @Setter
  @Value(AppConstant.PREFIX_MQ_EXCHANGE + "${connector.demo.send-data.exchange}" + AppConstant.SUFFIX_MQ_WORK)
  private String exchangeName;

  @Setter
  @Value(AppConstant.PREFIX_MQ_EXCHANGE + "${connector.demo.send-data.exchange}" + AppConstant.SUFFIX_MQ_DL_QUEUE)
  private String dlExchangeName;

  @Setter
  @Value("${connector.demo.send-data.routing-key}")
  private String routingKeyName;

  @Bean("directExchangeListener")
  public DirectExchange directExchangeListener(){
    return new DirectExchange(exchangeName);
  }

  @Bean("dlExchangeListener")
  public DirectExchange dlExchangeListener(){
    return new DirectExchange(dlExchangeName);
  }

  @Bean("queueListener")
  public Queue queueListener(){
    return QueueBuilder
      .durable(queueName)
      .withArgument(AppConstant.HEADER_X_DL_EXCHANGE, dlExchangeName)
      .withArgument(AppConstant.HEADER_X_DL_ROUTING_KEY, routingKeyName)
      .withArgument(AppConstant.HEADER_X_QUEUE_MODE, AppConstant.QueueMode.LAZY.getMode())
      .build();
  }

  @Bean("dlQueueListener")
  public Queue dlQueueListener(){
    return QueueBuilder
      .durable(dlQueueName)
      .withArgument(AppConstant.HEADER_X_DL_EXCHANGE, exchangeName)
      .withArgument(AppConstant.HEADER_X_DL_ROUTING_KEY, routingKeyName)
      .withArgument(AppConstant.HEADER_X_MESSAGE_TTL, AppConstant.SECONDS_TTL_DL_MESSAGE)
      .withArgument(AppConstant.HEADER_X_QUEUE_MODE, AppConstant.QueueMode.LAZY.getMode())
      .build();
  }

  @Bean("queueBindingListener")
  Binding queueBinding(
    @Qualifier("queueListener") Queue queueListener,
    @Qualifier("directExchangeListener") DirectExchange directExchangeListener
  ) {
    return BindingBuilder.bind(queueListener).to(directExchangeListener).with(routingKeyName);
  }

  @Bean("dlQueueBindingListener")
  Binding dlQueueBindingListener(
    @Qualifier("dlQueueListener") Queue dlQueueListener,
    @Qualifier("dlExchangeListener") DirectExchange dlExchangeListener
  ) {
    return BindingBuilder.bind(dlQueueListener).to(dlExchangeListener).with(routingKeyName);
  }

  @RabbitListener(
    containerFactory = "rabbitListenerContainerFactory",
    queues = AppConstant.PREFIX_MQ_QUEUE + "${connector.demo.send-data.queue}" + AppConstant.SUFFIX_MQ_WORK
  )
  public void receive(Message message) {
    String msgBody = new String(message.getBody());

    log.info("Message received :" + msgBody);

    try {
      DemoReadDto demoReadDto = mapper.readValue(msgBody, DemoReadDto.class);
    }catch(JsonProcessingException ex){
      throw new DemoException(AppConstant.ResponseConstant.DATA_PARSEBODYERROR);
    }

  }

  @RabbitListener(
    containerFactory = "rabbitListenerContainerFactory",
    queues = AppConstant.PREFIX_MQ_QUEUE + "${connector.demo.send-data.queue}" + AppConstant.SUFFIX_MQ_WORK
  )
  public void processFailedMessage(Message message){
    commonListener.processFailedMessage(message, rabbitTemplate, AppConstant.COUNT_MAX_RETRIES, routingKeyName);
  }

}
