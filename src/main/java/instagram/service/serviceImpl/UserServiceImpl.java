package instagram.service.serviceImpl;

import instagram.entity.Follower;
import instagram.entity.User;
import instagram.entity.UserInfo;
import instagram.exception.MyException;
import instagram.repository.UserRepository;
import instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void signUp(User newUser) {
        Follower follower = new Follower();
        UserInfo userInfo = new UserInfo();
        follower.setSubscriptions(new ArrayList<>());
        follower.setSubscribers(new ArrayList<>());
        newUser.setFollower(follower);
        newUser.setUserInfo(userInfo);
        userRepository.save(newUser);
    }

    @Override
    public User findUserByNameAndPassword(User user) throws MyException {
        User findUser = userRepository.findUserByNameAndPassword(user.getUserName(), user.getPassword());
        if (findUser != null) {
            return findUser;
        }
        throw new MyException();
    }

    @Override
    public List<User> findUserByUserName(Long userId, String keyword) throws MyException {
        List<User> users = userRepository.searchUsers("%" + keyword + "%");
        User findUser = findById(userId);
        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(findUser.getUserName())) {
                throw new MyException();

            }
        }
        return users;
    }
    @Override
    public void updateUser(Long id, User user) {
        User foundUser = findById(id);
        foundUser.setUserName(user.getUserName());
        foundUser.setEmail(user.getEmail());
        foundUser.setPassword(user.getPassword());
        UserInfo userInfo = foundUser.getUserInfo();
        userInfo.setBiography(user.getUserInfo().getBiography());
        userInfo.setFullName(user.getUserInfo().getFullName());
        userInfo.setImage(user.getUserInfo().getImage());
        userInfo.setGender(user.getUserInfo().getGender());
        userRepository.save(foundUser);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    public void deleteUserByPass(String password, Long userId) throws MyException {
        User foundUser = findById(userId);
        if (foundUser.getPassword().equals(password)){
            userRepository.deleteById(userId);
        }else throw new MyException();
    }
}
