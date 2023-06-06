package com.template.domain.services;

import com.template.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.template.domain.models.User;
import com.template.exceptions.UserNotFoundException;
import java.util.List;

/** Provide user related functionality. */
public class DomainUserService implements UserService {

  private final UserRepository userRepository;

  public DomainUserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public String getFullName(Long id) {
    var user = userRepository
          .findById(id)
          .orElseThrow();
    return user.getFirstName() + " " + user.getLastName();
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User createNew(User user) {
    return userRepository.save(user);
  }

  @Override
  public User getUser(Long id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
  }

  @Override
  public User updateUser(User newUser, Long id) {
    return userRepository.findById(id)
            .map(user -> {
              user.setFirstName(newUser.getFirstName());
              user.setLastName(newUser.getLastName());
              return userRepository.save(user);
            })
            .orElseGet(() -> {
              newUser.setId(id);
              return userRepository.save(newUser);
            });
  }

  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
