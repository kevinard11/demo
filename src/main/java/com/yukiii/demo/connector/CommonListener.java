package com.yukiii.demo.connector;

import com.yukiii.demo.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("commonListener")
public class CommonListener {

  public void processFailedMessage(
    Message message,
    RabbitTemplate rabbitTemplate,
    Integer maxRetriesCount,
    String exchange
  ) {
    Integer retryCount = (Integer) message.getMessageProperties().getHeaders().get(AppConstant.HEADER_X_RETRIES_COUNT);

    if(retryCount == null){
      retryCount = 1;
    }
    if(retryCount > maxRetriesCount){
      log.info("Discarding message");
      return;
    }

    log.info("Retrying message for the {} times", retryCount);
    message.getMessageProperties().getHeaders().put(AppConstant.HEADER_X_RETRIES_COUNT, ++retryCount);

    rabbitTemplate.send(exchange, message.getMessageProperties().getReceivedRoutingKey(), message);
  }
}
