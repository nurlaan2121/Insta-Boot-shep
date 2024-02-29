package instagram.service.serviceImpl;

import instagram.dto.ChatPes;
import instagram.dto.ChatView;
import instagram.dto.UserRep;
import instagram.entity.Chat;
import instagram.entity.Message;
import instagram.entity.User;
import instagram.repository.ChatRepo;
import instagram.repository.MessageRepo;
import instagram.repository.UserRepository;
import instagram.service.ChatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatSerImpl implements ChatService {
    private final ChatRepo chatRepo;
    private final UserRepository userRep;
    private final MessageRepo messageRepo;

    @Override
    public List<ChatPes> myChatHome(Long userId) {
        List<ChatPes> chatPes = chatRepo.myChatHome(userId);
        List<ChatPes> chatPes1 = chatRepo.myChatHome2(userId);
        chatPes1.addAll(chatPes);
        return chatPes1;
    }

    @Override
    public List<UserRep> getAllChats() {
        return chatRepo.getAllChats();
    }

    @Override
    public List<UserRep> searchUser(String keyword) {
        return chatRepo.search("%" + keyword + "%");
    }

    @Override
    public void createChat(Long chooseUserId, Long currentUserId) {
        Chat chat = new Chat();
        User currentUser = userRep.findById(currentUserId).get();
        User chooseUser = userRep.findById(chooseUserId).get();
        chat.setFirstUser(currentUser);
        chat.setSecondUser(chooseUser);
        chatRepo.save(chat);
    }

    @Override
    public void deleteByid(Long chatId) {
        Chat chat = chatRepo.findById(chatId).get();
        chatRepo.delete(chat);
    }

    @Override
    public List<ChatView> getAllChatsForChooseUser(Long chatId, Long currentUserId) {
        Chat chat = chatRepo.findById(chatId).get();
        Long id = chat.getSecondUser().getId();
        User chooseUser = userRep.findById(id).get();
        User currentUser = userRep.findById(currentUserId).get();
        List<ChatView> chatViews = new ArrayList<>();
        List<Message> firstUserSendMessages = chat.getFirstUserSendMessages();
        List<Message> secondUserSendMessages = chat.getSecondUserSendMessages();
        for (int i = 0; i < firstUserSendMessages.size(); i++) {
            chatViews.add(new ChatView(firstUserSendMessages.get(i).getId(), currentUser.getUserName(), firstUserSendMessages.get(i).getMessage(), firstUserSendMessages.get(i).getCrateAd()));
        }
        for (int i = 0; i < secondUserSendMessages.size(); i++) {
            chatViews.add(new ChatView(firstUserSendMessages.get(i).getId(), chooseUser.getUserName(), firstUserSendMessages.get(i).getMessage(), firstUserSendMessages.get(i).getCrateAd()));
        }
        chatViews.sort(comparator);
        return chatViews;
    }

    @Override
    @Transactional
    public void saveMess(Long currentUserId, Long chooseUserId, Message message) {
        Chat chat = chatRepo.findByIdIn(currentUserId, chooseUserId);
        messageRepo.save(message);
        if (!chat.getFirstUser().getId().equals(currentUserId)) {
            chat.getSecondUserSendMessages().add(message);
        } else {
            chat.getFirstUserSendMessages().add(message);

        }

    }

    @Override
    public Long getChooseUserIdbyChatId(Long chatId) {
        return chatRepo.get(chatId);
    }

    Comparator<ChatView> comparator = new Comparator<ChatView>() {
        @Override
        public int compare(ChatView o1, ChatView o2) {
            return o1.getCreatedAd().compareTo(o2.getCreatedAd());
        }
    };

}
