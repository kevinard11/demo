package com.yukiii.demo.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  @Value("${spring.rabbitmq.host}")
  private String host;

  @Value("${spring.rabbitmq.port}")
  private int port;

  @Value("${spring.rabbitmq.username}")
  private String username;

  @Value("${spring.rabbitmq.password}")
  private String password;

  @Bean("connectionFactory")
  public ConnectionFactory connectionFactory(){
    CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host, port);
    cachingConnectionFactory.setUsername(username);
    cachingConnectionFactory.setPassword(password);
    return cachingConnectionFactory;
  }

  @Bean("rabbitAdmin")
  public RabbitAdmin rabbitAdmin(
    @Qualifier("connectionFactory") ConnectionFactory connectionFactory
  ){
    return new RabbitAdmin(connectionFactory);
  }

  @Bean("messageConverter")
  public MessageConverter messageConverter(){
    return new Jackson2JsonMessageConverter();
  }

  @Bean("rabbitListenerContainerFactory")
  public RabbitListenerContainerFactory rabbitListenerContainerFactory(
    @Qualifier("connectionFactory") ConnectionFactory connectionFactory
  ) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMissingQueuesFatal(false);
    return factory;
  }

  @Bean("rabbitTemplate")
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());
    return rabbitTemplate;
  }
}
