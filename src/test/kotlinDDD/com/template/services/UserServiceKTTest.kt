package com.template.services

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.openMocks
import java.util.Optional
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import com.template.domain.models.User
import com.template.domain.repositories.UserRepository
import com.template.domain.services.DomainUserService
import com.template.exceptions.UserNotFoundException

class UserServiceKTTest {
  @Mock lateinit var userRepository: UserRepository
  
  lateinit var userService: DomainUserService
  
  @BeforeEach
  fun setUp(): Unit {
    openMocks(this)
    userService = spy(DomainUserService(userRepository))
  }
  
  @Test
  fun test_full_name_ok(): Unit {
	var existingUser = User("First", "Last", 1)
    `when`(userRepository.findById(1))
        .thenReturn(existingUser)
    
    var fullName = userService.getFullName(1)
    
    Assertions.assertEquals("First Last", fullName)
  }

  @Test
  fun test_full_name_when_user_doesnt_exist(): Unit {
	`when`(userRepository.findById(2))
        .thenReturn(null)
    var fullName = userService.getFullName(2)

    Assertions.assertEquals(null, fullName)
  }

  @Test
  fun test_find_all_ok(): Unit {
    var existingUser = User("First", "Last", 1)
    `when`(userRepository.findAll())
            .thenReturn(mutableListOf(existingUser))

    var all = userService.findAll()

    Assertions.assertEquals(mutableListOf(existingUser), all)
  }

  @Test
  fun test_find_all_returns_empty_list(): Unit {
    `when`(userRepository.findAll())
            .thenReturn(mutableListOf())

    var all = userService.findAll()

    Assertions.assertTrue(all.isEmpty())
  }

  @Test
  fun test_create_new_ok(): Unit {
    var user = User("First", "Last", 1)
    `when`(userRepository.save(user))
            .thenReturn(user)

    var newUser = userService.createNew(user)

    Assertions.assertEquals(user, newUser)
  }

  @Test
  fun test_get_user_ok(): Unit {
    var existingUser = User("First", "Last", 1)
    `when`(userRepository.findById(1))
            .thenReturn(existingUser)

    var user = userService.getUser(1)

    Assertions.assertEquals(existingUser, user)
  }

  @Test
  fun test_get_user_returns_null(): Unit {
    `when`(userRepository.findById(1))
            .thenReturn(null)

    val exception = Assertions.assertThrows(UserNotFoundException::class.java) {
      userService.getUser(1)
    }
  }

  @Test
  fun test_update_user_ok(): Unit {
    var existingUser = User("First", "Last", 1)
    `when`(userRepository.findById(1))
            .thenReturn(existingUser)
    var toUpdateUser = User("UpdatedFirst", "UpdatedLast")
    `when`(userRepository.save(existingUser))
            .thenReturn(toUpdateUser)

    var user = userService.updateUser(toUpdateUser, 1)

    Assertions.assertEquals("UpdatedFirst", user?.firstName)
    Assertions.assertEquals("UpdatedLast", user?.lastName)
  }

  @Test
  fun test_update_user_creates_new_user_when_not_existent(): Unit {
    `when`(userRepository.findById(1))
            .thenReturn(null)
    var toUpdateUser = User("UpdatedFirst", "UpdatedLast")
    `when`(userRepository.save(toUpdateUser))
            .thenReturn(toUpdateUser)

    var user = userService.updateUser(toUpdateUser, 1)

    Assertions.assertEquals("UpdatedFirst", user?.firstName)
    Assertions.assertEquals("UpdatedLast", user?.lastName)
  }

  @Test
  fun test_delete_user_ok(): Unit {
    doNothing().`when`(userRepository).deleteById(1)

    Assertions.assertDoesNotThrow() {
      userService.deleteUser(1)
    }
  }

  @Test
  fun test_delete_user_does_not_exist(): Unit {
    `when`(userRepository.deleteById(1)).thenAnswer { throw Exception("Not found") }

    val exception = Assertions.assertThrows(Exception::class.java) {
      userService.deleteUser(1)
    }

    Assertions.assertEquals("Not found", exception.message)
  }
}
