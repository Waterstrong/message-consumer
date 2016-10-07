package ws.message.configuration;

import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.rabbitmq.client.ConnectionFactory;

import ws.message.consumer.RabbitMqConsumer;

@Configuration
public class RabbitMqConfiguration {
    @Value("${message.queue.host}")
    private String queueHost;

    @Value("#{'${message.queue.names}'.split(',')}")
    private String[] queueNames;

    @Value("${message.queue.username}")
    private String username;

    @Value("${message.queue.password}")
    private String password;

    @Value("${message.queue.port:5672}")
    private int port;

    @Value("${message.queue.virtualHost:/}")
    private String virtualHost;

    @Value("${message.queue.recoveryInterval}")
    private Long recoveryInterval;

    @Value("${message.queue.receiveTimeout}")
    private Long receiveTimeout;

    @Bean
    SimpleMessageListenerContainer container(RabbitMqConsumer messageListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(queueNames);
        container.setMessageListener(messageListener);
        container.setRecoveryInterval(recoveryInterval);
        container.setReceiveTimeout(receiveTimeout);
        container.setDefaultRequeueRejected(false);
        return container;
    }

    private AbstractConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(queueHost);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setPort(port);
        factory.setVirtualHost(virtualHost);
        return new CachingConnectionFactory(factory);
    }

}
