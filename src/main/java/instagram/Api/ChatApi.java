package instagram.Api;

import instagram.dto.ChatPes;
import instagram.dto.ChatView;
import instagram.dto.UserRep;
import instagram.entity.Chat;
import instagram.entity.Message;
import instagram.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.sax.SAXResult;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/api")
public class ChatApi {
    private final ChatService chatService;

    @GetMapping("/myChatHome/{userId}")
    public String myChatHome(@PathVariable Long userId, Model model, Model currentUserId) {
        List<ChatPes> chats = chatService.myChatHome(userId);
        currentUserId.addAttribute("currentUserId", userId);
        model.addAttribute("myChats", chats);
        return "chatMain-page";
    }

    @GetMapping("/search/{currentUserId}")
    public String search(Model model, @PathVariable("currentUserId") Long currentUserId, Model model2) {
        List<UserRep> usersUSerName = chatService.getAllChats();
        model.addAttribute("users", usersUSerName);
        model2.addAttribute("currentUserId", currentUserId);
        return "addnewChat-page";
    }

    @GetMapping("/chat/search/{currentUserId}")
    String searchPosts(@RequestParam String keyword, Model model, @PathVariable("currentUserId") Long currentUserId, Model model2) {
        List<UserRep> searchUsers = chatService.searchUser(keyword);
        model.addAttribute("users", searchUsers);
        model2.addAttribute("currentUserId", currentUserId);
        return "addnewChat-page";
    }

    @GetMapping("/createChat/{chooseUserId}/{currentUserId}")
    public String createChat(@PathVariable Long chooseUserId, @PathVariable Long currentUserId) {
        chatService.createChat(chooseUserId, currentUserId);
        return "redirect:/chat/api/myChatHome/" + currentUserId;
    }

    @GetMapping("/deleteChat/{chatId}/{currentUserId}")
    public String deleteChat(@PathVariable Long chatId, @PathVariable Long currentUserId) {
        chatService.deleteByid(chatId);
        return "redirect:/chat/api/myChatHome/" + currentUserId;
    }

    @GetMapping("/viewChat/{chatId}/{currentUserId}")
    public String viewChat(@PathVariable Long chatId, @PathVariable Long currentUserId, Model model,Model model2,Model model3) {
        List<ChatView> allChats = chatService.getAllChatsForChooseUser(chatId, currentUserId);
        Long chooseUserId = chatService.getChooseUserIdbyChatId(chatId);
        model2.addAttribute("chooseUserId",chooseUserId);
        model3.addAttribute("message", new Message());
        model.addAttribute("allChats", allChats);
        return "chooseChat";
    }
    @PostMapping("/createMess/{currentUserId}/{chooseUserId}")
    private String createMess(@PathVariable Long currentUserId,@PathVariable Long chooseUserId,@RequestParam String message){
        Message newMessage = new Message();
        newMessage.setMessage(message);
        System.out.println("11");
        chatService.saveMess(currentUserId,chooseUserId,newMessage);
        System.out.println("22");
        return "redirect:/chat/api/myChatHome/" + currentUserId;
    }
}
