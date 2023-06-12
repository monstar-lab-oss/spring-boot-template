package com.template.domain.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.template.domain.models.User;
import com.template.exceptions.UserNotFoundException;
import java.util.List;

/** Provide user related functionality. */
public interface UserService {

  public String getFullName(Long id);

  public List<User> findAll();

  public User createNew(User user);

  public User getUser(Long id);

  public User updateUser(User newUser, Long id);

  public void deleteUser(Long id);
}
