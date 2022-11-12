package com.yukiii.demo.connector;

import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.entity.EventStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
public class PublishRunner implements Runnable{

  private final CommonPublisher commonPublisher;
  private final EventStore eventStore;
  private final RabbitTemplate rabbitTemplate;

  public PublishRunner(CommonPublisher commonPublisher, EventStore eventStore, RabbitTemplate rabbitTemplate) {
    this.commonPublisher = commonPublisher;
    this.eventStore = eventStore;
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public void run() {
    try{
      commonPublisher.send(
        rabbitTemplate,
        eventStore.getMessage(),
        eventStore.getKey().toString(),
        eventStore.getExchange(),
        eventStore.getRoutingKey()
      );
      commonPublisher.updatePublishedMessage(eventStore);
    }catch(AmqpException e){
      log.error(AppConstant.ResponseConstant.DATA_PUBLISHFAIL.getMessage(), e);
    }
  }
}
