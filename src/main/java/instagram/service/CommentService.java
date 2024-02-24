package instagram.service;

import instagram.entity.Comment;

public interface CommentService {
    void saveComment(Long userId, Long postId, Comment comment);

    void deleteComment(Long comId);
}
