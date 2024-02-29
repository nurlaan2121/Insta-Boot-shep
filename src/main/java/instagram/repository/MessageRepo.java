package instagram.repository;

import instagram.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo  extends JpaRepository<Message,Long> {
}
