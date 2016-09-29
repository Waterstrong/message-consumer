package ws.message.consumer;

import static org.apache.log4j.Logger.getLogger;
import static org.joda.time.DateTime.now;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import ws.message.entity.Message;
import ws.message.repository.MessageRepository;

@Transactional
public class RabbitMqConsumer extends DefaultConsumer {
    private final static Logger LOGGER = getLogger(RabbitMqConsumer.class);

    @Autowired
    private MessageRepository messageRepository;

    public RabbitMqConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        LOGGER.info(String.format("@%s Received '%s': '%s'", now(), envelope.getRoutingKey(), message));
        messageRepository.save(new Message(message));
    }
}
