package instagram.service.serviceImpl;

import instagram.entity.Like;
import instagram.entity.Post;
import instagram.entity.User;
import instagram.exception.MyException;
import instagram.repository.PostRepository;
import instagram.repository.UserRepository;
import instagram.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Override
    public void createPost(Long userId, Post newPOst) throws MyException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        if (newPOst.getImage() != null){
            Like like = new Like();
            newPOst.setLike(like);
            user.getPosts().add(newPOst);
            newPOst.setUser(user);
            postRepository.save(newPOst);
        }else {
            throw new MyException();
        }
    }
    @Override
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));
    }
    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }
    @Override
    public void updatePOst(Long postId, Post post) {
        Post findPost = findById(postId);
        findPost.setTitle(post.getTitle());
        findPost.setDescription(post.getDescription());
        postRepository.save(findPost);
    }
    @Override
    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    @Transactional
    public void getLikePost(Long currentUserId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();

        List<Long> isLikes = post.getLike().getIsLikes();
        if (isLikes.contains(currentUserId)){
            isLikes.remove(currentUserId);
        }else {
            isLikes.add(currentUserId);
        }
    }
}
