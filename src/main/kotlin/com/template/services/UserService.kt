package com.template.services

import com.template.repositories.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import com.template.models.User
import java.util.Optional
import com.template.exceptions.UserNotFoundException
import org.springframework.data.repository.CrudRepository

/** Provide user related functionality. */
@Service
@RequiredArgsConstructor
class UserService(val userRepository: UserRepository) {
  fun getFullName(id: Long): String {
    val user = userRepository
          .findById(id)
          .orElseThrow()
    return user.firstName + " " + user.lastName
  }

  fun findAll(): Iterable<User> {
    return userRepository.findAll()
  }

  fun createNew(user: User): User {
    return userRepository.save(user)
  }

  fun getUser(id: Long): User? {
    return when(val user = userRepository.findById(id)) {
      Optional.ofNullable(null) -> {
        return null
      }
      else -> {
        return user.get()
      }
    }
  }

  fun updateUser(newUser: User, id: Long): User {
    return when(val user = userRepository.findById(id)) {
      Optional.ofNullable(null) -> {
        newUser.id = id
        return userRepository.save(newUser)
      }
      else -> {
        var userObj = user.get()
        userObj.firstName = newUser.firstName
        userObj.lastName = newUser.lastName
        return userRepository.save(userObj)
      }
    }
  }

  fun deleteUser(id: Long) {
    userRepository.deleteById(id)
  }
}

