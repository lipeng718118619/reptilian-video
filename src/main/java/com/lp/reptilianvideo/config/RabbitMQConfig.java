package com.lp.reptilianvideo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig
{
    static final String topicExchangeName = "reptilian-video-exchange";

    static final String queueName = "reptilian-video";

    public static final String TOPIC = "reptilian-video";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    Queue queue()
    {
        return new Queue(queueName, true);
    }

    @Bean
    TopicExchange exchange()
    {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange)
    {
        return BindingBuilder.bind(queue).to(exchange).with(TOPIC);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter)
    {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        container.setConcurrentConsumers(5);
        container.setErrorHandler(e -> logger.error(e.getMessage(), e));
        container.setAutoStartup(true);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }
}
