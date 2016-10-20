package ws.message.configuration;

import java.net.URI;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ws.message.consumer.RabbitMqConsumer;

@Configuration
public class RabbitMqConfiguration {
    @Value("${rabbitmq.uri}")
    private String rabbitUri;

    @Value("#{'${rabbitmq.queueNames}'.split(',')}")
    private String[] queueNames;

    @Value("${rabbitmq.recoveryInterval}")
    private Long recoveryInterval;

    @Value("${rabbitmq.receiveTimeout}")
    private Long receiveTimeout;

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, RabbitMqConsumer messageListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueNames);
        container.setMessageListener(messageListener);
        container.setRecoveryInterval(recoveryInterval);
        container.setReceiveTimeout(receiveTimeout);
        container.setDefaultRequeueRejected(false);
        return container;
    }

    @Bean
    ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(URI.create(rabbitUri));
    }

}
