package com.template.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.MockitoAnnotations.openMocks;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.template.models.User;
import com.template.repositories.UserRepository;
import com.template.exceptions.UserNotFoundException;

public class UserServiceTest {
  @Mock private UserRepository userRepository;

  @Mock private UserService userService;

  @BeforeEach
  public void setUp() {
    openMocks(this);
    userService = spy(new UserService(userRepository));
  }

  @Test
  public void test_full_name() {
	Long id = (long) 1;
	User existingUser = new User();
	existingUser.setId(id);
    existingUser.setFirstName("First");
    existingUser.setLastName("Last");
    when(userRepository.findById(any()))
        .thenReturn(Optional.of(existingUser));

    String fullName = userService.getFullName(id);

    Assertions.assertEquals("First Last", fullName);
  }

  @Test
  public void test_full_name_when_user_doesnt_exist() {
	Long id = (long) 1;
	User existingUser = new User();
	existingUser.setId(id);
    existingUser.setFirstName("First");
    existingUser.setLastName("Last");
    when(userRepository.findById(any()))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(Exception.class, () -> userService.getFullName((long) 2));
  }

  @Test
  public void test_find_all() {
      Long id = (long) 1;
      User existingUser = new User();
      existingUser.setId(id);
      existingUser.setFirstName("First");
      existingUser.setLastName("Last");
      when(userRepository.findAll())
              .thenReturn(List.of(existingUser));

      List<User> all = userService.findAll();

      Assertions.assertEquals(List.of(existingUser), all);
  }

  @Test
  public void test_find_all_returns_empty() {
      when(userRepository.findAll())
              .thenReturn(new ArrayList<>());

      List<User> all = userService.findAll();

      Assertions.assertEquals(0, all.size());
  }

  @Test
  public void test_create_new() {
      Long id = (long) 1;
      User newUser = new User();
      newUser.setId(id);
      newUser.setFirstName("First");
      newUser.setLastName("Last");
      when(userRepository.save(newUser))
              .thenReturn(newUser);

      User user = userService.createNew(newUser);

      Assertions.assertEquals(newUser, user);
  }

  @Test
  public void test_get_user() {
      Long id = (long) 1;
      User existingUser = new User();
      existingUser.setId(id);
      existingUser.setFirstName("First");
      existingUser.setLastName("Last");
      when(userRepository.findById(id))
              .thenReturn(Optional.of(existingUser));

      User user = userService.getUser(id);

      Assertions.assertEquals(existingUser, user);
  }

  @Test
  public void test_get_user_returns_empty() {
      when(userRepository.findById((long) 2))
              .thenReturn(Optional.empty());

      Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUser((long) 2));
  }

  @Test
  public void test_update_user() {
      Long id = (long) 1;
      User existingUser = new User();
      existingUser.setId(id);
      existingUser.setFirstName("First");
      existingUser.setLastName("Last");
      when(userRepository.findById(id))
              .thenReturn(Optional.of(existingUser));

      User newUser = new User();
      newUser.setId(id);
      newUser.setFirstName("New");
      newUser.setLastName("User");
      when(userRepository.save(newUser))
              .thenReturn(newUser);

      User user = userService.updateUser(newUser, id);

      Assertions.assertEquals(newUser, user);
  }

  @Test
  public void test_update_user_creates_new_user() {
      Long id = (long) 1;
      User newUser = new User();
      newUser.setId(id);
      newUser.setFirstName("New");
      newUser.setLastName("User");
      when(userRepository.save(newUser))
              .thenReturn(newUser);

      User user = userService.updateUser(newUser, id);

      Assertions.assertEquals(newUser, user);
  }

  @Test
  public void test_delete_user() {
      Long id = (long) 1;
      User existingUser = new User();
      existingUser.setId(id);
      existingUser.setFirstName("First");
      existingUser.setLastName("Last");

      Assertions.assertDoesNotThrow(() -> userService.deleteUser(id));
  }
}
