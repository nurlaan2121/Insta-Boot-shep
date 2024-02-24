package instagram.Api;

import instagram.entity.Comment;
import instagram.entity.Image;
import instagram.entity.Post;
import instagram.entity.User;
import instagram.exception.MyException;
import instagram.service.CommentService;
import instagram.service.PostService;
import instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostApi {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/newPost/{userId}")
    public String createPost(@PathVariable Long userId, Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("image", new Image());
        model.addAttribute("userId", userId);
        return "new-post";
    }

    @PostMapping("/savePost/{userId}")
    public String savePost(@ModelAttribute("post") Post post,
                           @ModelAttribute("image") Image image,
                           @PathVariable Long userId,
                           Model model) {
        try {
            post.setImage(image);
            image.setPost(post);
            postService.createPost(userId,post);
        } catch (MyException e) {
            model.addAttribute("errorMessage", "New Post cannot be without Image");
            return "error-page";
        }
        return "redirect:/home/profUser/" + userId;
    }

    @GetMapping("/viewComment/{userId}/{postId}")
    public String viewComment(Model model, @PathVariable Long postId,
                              @PathVariable Long userId) {
        Post findPost = postService.findById(postId);
        model.addAttribute("findPost", findPost);
        model.addAttribute("postId", postId);
        model.addAttribute("userId", userId);
        model.addAttribute("newComment", new Comment());
        return "comment-page";
    }

    @PostMapping("/savedComment/{userId}/{postId}")
    public String savedComment(@PathVariable Long postId,
                               @PathVariable Long userId,
                               @ModelAttribute("newComment") Comment comment,
                               Model model) {
        model.addAttribute("postId", postId);
        commentService.saveComment(userId, postId, comment);
        return "redirect:/posts/viewComment/"+ userId +"/"+ postId;
    }

    @GetMapping("/editPost/{userId}/{postId}")
    public String editPost(@PathVariable Long postId,
                           @PathVariable Long userId, Model model) {
        Post findPOst = postService.findById(postId);
        model.addAttribute("postGetId", postId);
        model.addAttribute("userId", userId);
        model.addAttribute("editPost", findPOst);
        return "editPost-page";
    }

    @PostMapping("/savePOstAfter/{userId}/{postId}")
    public String savePostAfterEdit(@PathVariable Long postId,
                                    @PathVariable Long userId,
                                    @ModelAttribute("editPost") Post post) {
        postService.updatePOst(postId, post);
        return "redirect:/home/profUser/" + userId;
    }

    @GetMapping("/deletePost/{userId}/{postId}")
    public String deletePost(@PathVariable Long postId,
                             @PathVariable Long userId) {
        postService.deletePostById(postId);
        return "redirect:/home/profUser/" + userId;
    }

    @GetMapping("/deleteComment/{userId}/{postId}/{comId}")
    public String deleteComment(@PathVariable Long comId,
                                @PathVariable Long userId,
                                @PathVariable Long postId){
        commentService.deleteComment(comId);
        return "redirect:/posts/viewComment/" + userId +"/"+ postId;
    }

    @GetMapping("/likes/{userId}/{postId}")
    public String isLike(@PathVariable Long postId,
                         @PathVariable Long userId){
        postService.getLikePost(userId, postId);
        return "redirect:/reg/main/" + userId;
    }
}
