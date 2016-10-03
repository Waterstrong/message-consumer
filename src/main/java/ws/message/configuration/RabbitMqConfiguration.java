package ws.message.configuration;

import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ws.message.consumer.RabbitMqConsumer;

@Configuration
public class RabbitMqConfiguration {
    @Value("${message.queue.host}")
    private String queueHost;

    @Value("${message.queue.name}")
    private String queueName;

//    @Bean
//    Queue queue() {
//        return new Queue(queueName);
//    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
//        container.setQueues(queue);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    AbstractConnectionFactory connectionFactory() {
        com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
        factory.setHost(queueHost);
        return new CachingConnectionFactory(factory);
    }

    @Bean
    RabbitMqConsumer rabbitMqConsumer() {
        return new RabbitMqConsumer();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RabbitMqConsumer rabbitMqConsumer) {
        return new MessageListenerAdapter(rabbitMqConsumer, "onMessage");
    }

}
