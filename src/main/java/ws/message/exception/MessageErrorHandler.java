package ws.message.exception;

import static java.lang.String.format;
import static org.apache.log4j.Logger.getLogger;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component
public class MessageErrorHandler implements ErrorHandler {
    private final static Logger LOGGER = getLogger(MessageErrorHandler.class);

    @Override
    public void handleError(Throwable t) {
        LOGGER.error(format("=======Error: %s\n\n", t.getMessage()));
    }
}
