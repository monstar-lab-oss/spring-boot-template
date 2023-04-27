package com.template.services

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.openMocks
import java.util.Optional
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import com.template.models.User
import com.template.repositories.UserRepository

class UserServiceKTTest {
  @Mock lateinit var userRepository: UserRepository
  
  lateinit var userService: UserService
  
  @BeforeEach
  fun setUp(): Unit {
    openMocks(this)
    userService = spy(UserService(userRepository))
  }
  
  @Test
  fun test_full_name_ok(): Unit {
	var existingUser = User("First", "Last", 1)
    `when`(userRepository.findById(any()))
        .thenReturn(Optional.of(existingUser))
    
    var fullName = userService.getFullName(1)
    
    Assertions.assertEquals("First Last", fullName)
  }

  @Test
  fun test_full_name_when_user_doesnt_exist(): Unit {
	`when`(userRepository.findById(any()))
        .thenReturn(Optional.empty())
    val exception = Assertions.assertThrows(Exception::class.java) {
      userService.getFullName(2)
	}
	Assertions.assertEquals("No value present", exception.message)
  }

  @Test
  fun test_find_all_ok(): Unit {
    var existingUser = User("First", "Last", 1)
    `when`(userRepository.findAll())
            .thenReturn(listOf(existingUser))

    var all = userService.findAll()

    Assertions.assertEquals(listOf(existingUser), all)
  }

  @Test
  fun test_find_all_returns_empty_list(): Unit {
    `when`(userRepository.findAll())
            .thenReturn(listOf())

    var all = userService.findAll()

    Assertions.assertEquals("EmptyList", all::class.simpleName)
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
            .thenReturn(Optional.of(existingUser))

    var user = userService.getUser(1)

    Assertions.assertEquals(existingUser, user)
  }

  @Test
  fun test_get_user_returns_null(): Unit {
    `when`(userRepository.findById(1))
            .thenReturn(Optional.ofNullable(null))

    var user = userService.getUser(1)

    Assertions.assertEquals(null, user)
  }

  @Test
  fun test_update_user_ok(): Unit {
    var existingUser = User("First", "Last", 1)
    `when`(userRepository.findById(1))
            .thenReturn(Optional.of(existingUser))
    var toUpdateUser = User("UpdatedFirst", "UpdatedLast")
    `when`(userRepository.save(existingUser))
            .thenReturn(toUpdateUser)

    var user = userService.updateUser(toUpdateUser, 1)

    Assertions.assertEquals("UpdatedFirst", user.firstName)
    Assertions.assertEquals("UpdatedLast", user.lastName)
  }

  @Test
  fun test_update_user_creates_new_user_when_not_existent(): Unit {
    `when`(userRepository.findById(1))
            .thenReturn(Optional.ofNullable(null))
    var toUpdateUser = User("UpdatedFirst", "UpdatedLast")
    `when`(userRepository.save(toUpdateUser))
            .thenReturn(toUpdateUser)

    var user = userService.updateUser(toUpdateUser, 1)

    Assertions.assertEquals("UpdatedFirst", user.firstName)
    Assertions.assertEquals("UpdatedLast", user.lastName)
  }

  @Test
  fun test_delete_user_ok(): Unit {
    doNothing().`when`(userRepository).deleteById(any())

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
