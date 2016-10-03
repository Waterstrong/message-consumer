package ws.message.consumer;

import static java.lang.String.format;
import static org.apache.log4j.Logger.getLogger;
import static org.joda.time.DateTime.now;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

//@Transactional
public class RabbitMqConsumer {
    private final static Logger LOGGER = getLogger(RabbitMqConsumer.class);

//    @Autowired
//    private MessageRepository messageRepository;

    private CountDownLatch latch = new CountDownLatch(1);

    public void onMessage(String message) {
        LOGGER.info(format("@%s Received: %s", now(), message));
//        messageRepository.save(new Message(message));
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
