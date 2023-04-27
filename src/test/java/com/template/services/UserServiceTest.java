package com.template.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.template.models.User;
import com.template.repositories.UserRepository;

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
}
