package instagram.repository;

import instagram.dto.ChatPes;
import instagram.dto.UserRep;
import instagram.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.net.UnknownServiceException;
import java.util.List;

public interface ChatRepo extends JpaRepository<Chat, Long> {
    @Query("select new instagram.dto.ChatPes(c.id,c.firstUser.userName,c.secondUser.userName) from Chat c join User u on c.secondUser.id = u.id where u.id  = :userId")
    List<ChatPes> myChatHome(Long userId);

    @Query("select new instagram.dto.ChatPes(c.id,c.firstUser.userName,c.secondUser.userName) from Chat c join User u on c.firstUser.id = u.id where u.id  = :userId")
    List<ChatPes> myChatHome2(Long userId);

    @Query("select new instagram.dto.UserRep(u.id,u.userName) from User u")
    List<UserRep> getAllChats();

    @Query("select new instagram.dto.UserRep(u.id,u.userName) from User u where u.userName ilike (:s)")
    List<UserRep> search(String s);

    @Query("select c from Chat c where c.firstUser.id = :currentUserId or c.secondUser.id = :chooseUserId")
    Chat findByIdIn(Long currentUserId, Long chooseUserId);
    @Query("select c.secondUser.id from  Chat  c where c.id = :chatId")
    Long get(Long chatId);
}
