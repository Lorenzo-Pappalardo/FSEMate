package it.health.fsemate.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping("/user/add")
  public ResponseEntity<User> addUser(@Valid @RequestBody User user) throws URISyntaxException {
    if (userService.getUser(user.getCf()).isPresent()) {
      return ResponseEntity.badRequest().build();
    }
    userService.saveUser(user);
    return ResponseEntity.created(new URI("/user?cf=" + user.getCf())).build();
  }

  @GetMapping("/user")
  public ResponseEntity<Optional<User>> getUser(@Valid @RequestParam String cf) {
    if (userService.getUser(cf).isPresent()) {
      return ResponseEntity.ok(userService.getUser(cf));
    }
    return ResponseEntity.badRequest().build();
  }

  @GetMapping("/users")
  public ResponseEntity<Iterable<User>> getUsers() {
    return ResponseEntity.ok(userService.getUsers());
  }

  @PostMapping("/user/edit")
  public ResponseEntity<Object> editUser(@Valid @RequestBody User user) {
    userService.editUser(user);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/user/delete")
  public ResponseEntity<Object> deleteUser(@Valid @RequestParam String cf) {
    if (userService.getUser(cf).isPresent()) {
      userService.deleteUser(cf);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }
}
