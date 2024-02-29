package instagram.service;

import instagram.dto.ChatPes;
import instagram.dto.ChatView;
import instagram.dto.UserRep;
import instagram.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public interface ChatService {
    List<ChatPes> myChatHome(Long userId);

    List<UserRep> getAllChats();

    List<UserRep> searchUser(String keyword);

    void createChat(Long chooseUserId, Long currentUserId);

    void deleteByid(Long chatId);

    List<ChatView> getAllChatsForChooseUser(Long chatId, Long currentUserId);

    void saveMess(Long currentUserId, Long chooseUserId, Message message);

    Long getChooseUserIdbyChatId(Long chatId);

}
