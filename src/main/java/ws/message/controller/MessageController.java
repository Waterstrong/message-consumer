package ws.message.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ws.message.repository.MessageRepository;

@RestController
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @RequestMapping(method = GET, value = "/")
    public ResponseEntity<?> getMessage() {
        return new ResponseEntity<>(messageRepository.findAll(), OK);
    }
}
