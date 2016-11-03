package ws.message.consumer;

import static java.lang.String.format;
import static org.apache.log4j.Logger.getLogger;
import static org.joda.time.DateTime.now;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ws.message.repository.MessageRepository;

@Component
public class RabbitMqConsumer implements MessageListener {
    private final static Logger LOGGER = getLogger(RabbitMqConsumer.class);

    @Autowired
    private MessageRepository messageRepository;

    @Transactional
    @Override
    public void onMessage(Message message) {
        String bodyContent = new String(message.getBody());
        LOGGER.info(format("@@@@@@@@%s Received: %s\n", now(), bodyContent));
        messageRepository.save(new ws.message.entity.Message(bodyContent));

        if(bodyContent.equals("error")) {
            throw new RuntimeException("============Simulate Error\n\n");
        }
    }
}
