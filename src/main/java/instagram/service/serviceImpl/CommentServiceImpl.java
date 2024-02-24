package instagram.service.serviceImpl;

import instagram.entity.Comment;
import instagram.entity.Like;
import instagram.entity.Post;
import instagram.entity.User;
import instagram.repository.CommentRepository;
import instagram.repository.PostRepository;
import instagram.repository.UserRepository;
import instagram.service.CommentService;
import instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Override
    public void saveComment(Long userId, Long postId, Comment comment) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        user.getComments().add(comment);
        comment.setUser(user);
        post.getComments().add(comment);
        comment.setPost(post);
        commentRepository.save(comment);
    }
    @Override
    public void deleteComment(Long comId) {
        commentRepository.deleteById(comId);
    }
}
