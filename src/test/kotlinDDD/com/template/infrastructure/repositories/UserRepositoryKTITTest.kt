package com.template.infrastructure.repositories

import com.template.IntegrationRepositoryKTTests
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import com.template.domain.models.User
import com.template.domain.repositories.UserRepository
import com.template.infrastructure.repositories.JpaUserRepository
import java.util.Optional

class UserRepositoryKTITTest(): IntegrationRepositoryKTTests() {
  
  @Autowired
  lateinit var repository: JpaUserRepository

  @Test
  @Sql(
    scripts = [
      "classpath:scripts/create_user.sql"
    ])
  fun should_return_record(): Unit {
      val userFound = repository.findById(1)
	  assertEquals(1, userFound.get().id)
	  assertEquals("Firstname", userFound.get().firstName)
	  assertEquals("Lastname", userFound.get().lastName)
  }
	
  @Test
  fun should_not_return_record(): Unit {
      val userFound = repository.findById(1)
	  
	  assertEquals(Optional.empty<Any>(), userFound)
  }

  @Test
  @Sql(
    scripts = [
        "classpath:scripts/create_user.sql"
    ])
  fun find_all_should_return_a_record(): Unit {
    val usersFound = repository.findAll()
    assertEquals(1, usersFound.first()?.id)
    assertEquals("Firstname", usersFound.first()?.firstName)
    assertEquals("Lastname", usersFound.first()?.lastName)
  }

  @Test
  fun find_all_should_return_empty_list(): Unit {
    val usersFound = repository.findAll()

    assertEquals(null, usersFound.firstOrNull())
  }

  @Test
  fun save_should_return_a_record(): Unit {
    var user = User("First", "Last", 1)
    val usersFound = repository.save(user)

    assertEquals(1, usersFound.id)
    assertEquals("First", usersFound.firstName)
    assertEquals("Last", usersFound.lastName)
  }

  @Test
  @Sql(
    scripts = [
      "classpath:scripts/create_user.sql"
    ])
  fun delete_should_remove_record(): Unit {
    repository.deleteById(1)

    var users = repository.findAll()
    assertEquals(null, users.firstOrNull())
  }
}
