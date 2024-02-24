package instagram.Api;

import instagram.entity.Post;
import instagram.entity.User;
import instagram.exception.MyException;
import instagram.service.FollowerService;
import instagram.service.PostService;
import instagram.service.UserService;
import instagram.service.serviceImpl.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class MainApi {
    private final UserService userService;
    private final PostService postService;
    private final FollowerService followerService;
    @GetMapping("/profUser/{userId}")
    public String profilePage(Model model, @PathVariable Long userId) {
        try {
            User cerruntUser = userService.findById(userId);
            Long id = cerruntUser.getId();
            int subscribers = followerService.getNumberOfSubscribers(id);
            int subscriptions = followerService.getNumberOfSubscriptions(id);
            List<Post> posts = new ArrayList<>(cerruntUser.getPosts());
            Collections.reverse(posts);
            model.addAttribute("subscribers", subscribers);
            model.addAttribute("posts", posts);
            model.addAttribute("subscriptions", subscriptions);
            model.addAttribute("currentUser", cerruntUser);
            model.addAttribute("userId", id);
            return "profile-page";
        }catch (Exception e){
            return "error-page";
        }
    }

    @GetMapping("/editProf/{userId}")
    public String editUser(Model model, @PathVariable Long userId){
        User user = userService.findById(userId);
        model.addAttribute("current", user);
        model.addAttribute("userId", userId);
        return "edit-profile";
    }

    @PostMapping("/saveProfiles/{userId}")
    public String saveProfile(@ModelAttribute("current") User currentUser,
                              Model model,
                              @PathVariable Long userId) {
        try {
            userService.updateUser(userId, currentUser);
            return "redirect:/home/profUser/" + userId;
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Duplicate email or username. Please choose another one.");
            return "error-page";
        }
    }

    @GetMapping("/like/{userId}/{postId}")
    public String Like(@PathVariable Long postId,
                       @PathVariable Long userId){
        postService.getLikePost(userId, postId);
        return "redirect:/home/profUser/" + userId;
    }
}