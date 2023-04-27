package com.template.web;

import java.util.List;
import com.template.services.UserService;
import com.template.models.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

@RestController
@RequiredArgsConstructor
class UserController {

  private final UserService service;

  @GetMapping("/users")
  List<User> all() {
    return service.findAll();
  }

  @PostMapping("/users")
  User newUser(@RequestBody User newUser) {
    return service.createNew(newUser);
  }

  @GetMapping("/users/{id}")
  User one(@PathVariable Long id) {
    return service.getUser(id);
  }

  @PutMapping("/users/{id}")
  User updateUser(@RequestBody User newUser, @PathVariable Long id) {
    return service.updateUser(newUser, id);
  }

  @DeleteMapping("/users/{id}")
  void deleteUser(@PathVariable Long id) {
    service.deleteUser(id);
  }
}
