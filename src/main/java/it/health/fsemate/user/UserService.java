package it.health.fsemate.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public User saveUser(User user) {
    return userRepository.save(user);
  }

  public Optional<User> getUser(String cf) {
    return userRepository.findById(cf);
  }

  public List<User> getUsers() {
    List<User> users = new ArrayList<>();
    userRepository.findAll().forEach(users::add);
    return users;
  }

  public User editUser(User after) {
    return userRepository.save(after);
  }

  public void deleteUser(String cf) {
    userRepository.deleteById(cf);
  }
}
