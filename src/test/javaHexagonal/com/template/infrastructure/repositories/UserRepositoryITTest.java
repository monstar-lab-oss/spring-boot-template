package com.template.infrastructure.repositories;

import com.template.IntegrationRepositoryTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import com.template.infrastructure.repositories.PostgresqlDBUserRepository;
import com.template.domain.repositories.UserRepository;
import com.template.infrastructure.repositories.JpaUserRepository;
@Sql(
    scripts = {
      "classpath:scripts/create_user.sql"
    })
public class UserRepositoryITTest extends IntegrationRepositoryTests {

  @Autowired private JpaUserRepository userRepository;

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
