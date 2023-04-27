package com.template.repositories;

import com.template.IntegrationRepositoryTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Sql(
    scripts = {
      "classpath:scripts/create_user.sql"
    })
public class UserRepositoryITTest extends IntegrationRepositoryTests {

  @Autowired private UserRepository userRepository;

  @Test
  public void should_return_record() {
    var user = userRepository.findById((long) 1);
    assertTrue(user.isPresent());
  }
  
  @Test
  public void should_not_return_record() {
	  var user = userRepository.findById((long) 2);
	  
      assertFalse(user.isPresent());
  }
}
