package ws.message.consumer;

import static java.lang.String.format;
import static org.apache.log4j.Logger.getLogger;
import static org.joda.time.DateTime.now;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ws.message.entity.Message;
import ws.message.repository.MessageRepository;

@Component
public class RabbitMqConsumer {
    private final static Logger LOGGER = getLogger(RabbitMqConsumer.class);

    @Autowired
    private MessageRepository messageRepository;

    @Transactional
    public void onMessage(String message) {
        LOGGER.info(format("@%s Received: %s", now(), message));
        messageRepository.save(new Message(message));
    }
}
