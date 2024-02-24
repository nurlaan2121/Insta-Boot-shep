package instagram.service;

import instagram.entity.User;
import instagram.exception.MyException;

import java.util.List;

public interface UserService {
    void signUp(User newUser);
    User findUserByNameAndPassword(User user) throws MyException;
    List<User> findUserByUserName(Long userId, String keyword) throws MyException;
    void updateUser(Long id, User user);
    User findById(Long userId);
    void deleteUserByPass(String password, Long userId) throws MyException;
}
