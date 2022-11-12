package com.yukiii.demo.connector;

import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.dto.demo.DemoReadDto;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component("demoPublisher")
public class DemoPublisher {

  @Autowired
  @Qualifier("rabbitTemplate")
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private CommonPublisher commonPublisher;

  @Setter
  @Value(AppConstant.PREFIX_MQ_QUEUE + "${connector.demo.send-data.queue}" + AppConstant.SUFFIX_MQ_WORK)
  private String queueName;

  @Setter
  @Value(AppConstant.PREFIX_MQ_EXCHANGE + "${connector.demo.send-data.exchange}" + AppConstant.SUFFIX_MQ_WORK)
  private String exchangeName;

  @Setter
  @Value(AppConstant.PREFIX_MQ_EXCHANGE + "${connector.demo.send-data.exchange}" + AppConstant.SUFFIX_MQ_DL_QUEUE)
  private String dlExchangeName;

  @Setter
  @Value("${connector.demo.send-data.routing-key}")
  private String routingKeyName;

  @Bean("directExchangePublisher")
  public DirectExchange directExchangePublisher(){
    return new DirectExchange(exchangeName);
  }

  @Bean("queuePublisher")
  public Queue queuePublisher(){
    return QueueBuilder
      .durable(queueName)
      .withArgument(AppConstant.HEADER_X_DL_EXCHANGE, dlExchangeName)
      .withArgument(AppConstant.HEADER_X_DL_ROUTING_KEY, routingKeyName)
      .withArgument(AppConstant.HEADER_X_QUEUE_MODE, AppConstant.QueueMode.LAZY.getMode())
      .build();
  }

  @Bean("bindingPublisher")
  public Binding bindingPublisher(
    @Qualifier("queuePublisher") Queue queuePublisher, @Qualifier("directExchangePublisher") DirectExchange directExchangePublisher){
    return BindingBuilder.bind(queuePublisher).to(directExchangePublisher).with(routingKeyName);
  }

  public void send(DemoReadDto message, String idempotenceKey){
    commonPublisher.send(rabbitTemplate, message, idempotenceKey, exchangeName, routingKeyName);
  }

  public void republishUnsentMessage(){
    commonPublisher.republishUnsentMessage(rabbitTemplate, exchangeName, routingKeyName);
  }
}
