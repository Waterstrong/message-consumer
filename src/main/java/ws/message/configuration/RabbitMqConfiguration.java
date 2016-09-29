package ws.message.configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

import ws.message.consumer.RabbitMqConsumer;

@Configuration
public class RabbitMqConfiguration {
    @Value("${message.queue.host}")
    private String queueHost;

    @Value("${message.queue.name}")
    private String queueName;

    @Bean
    public Consumer consumer() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(queueHost);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName, true, false, false, null);
        Consumer consumer = new RabbitMqConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
        return consumer;
    }

}
