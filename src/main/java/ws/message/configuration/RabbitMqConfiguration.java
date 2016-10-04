package ws.message.configuration;

import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.rabbitmq.client.ConnectionFactory;

import ws.message.consumer.RabbitMqConsumer;

@Configuration
public class RabbitMqConfiguration {
    @Value("${message.queue.host}")
    private String queueHost;

    @Value("${message.queue.name}")
    private String queueName;

    @Bean
    SimpleMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RabbitMqConsumer rabbitMqConsumer) {
        return new MessageListenerAdapter(rabbitMqConsumer, "onMessage");
    }

    private AbstractConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(queueHost);
        return new CachingConnectionFactory(factory);
    }

}
