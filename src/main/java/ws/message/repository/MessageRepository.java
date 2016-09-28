package ws.message.repository;

import org.springframework.data.repository.CrudRepository;

import ws.message.entity.Message;

public interface MessageRepository extends CrudRepository<Message, Long>{
}
