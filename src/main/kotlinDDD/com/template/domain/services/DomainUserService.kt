package com.template.domain.services

import com.template.domain.repositories.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import com.template.domain.models.User
import com.template.exceptions.UserNotFoundException
import kotlin.collections.MutableList

/** Provide user related functionality.  */
class DomainUserService(userRepository: UserRepository) : UserService {
    private val userRepository: UserRepository

    init {
        this.userRepository = userRepository
    }

    override fun getFullName(id: Long): String? {
        val user = userRepository.findById(id)
        if (user != null)
            return (user.firstName + " " + user.lastName)
        else return null
    }

    override fun findAll(): MutableList<User?> {
        return userRepository.findAll()
    }

    override fun createNew(user: User): User? {
        return userRepository.save(user)
    }

    override fun getUser(id: Long): User? {
        val user = userRepository.findById(id)
        if (user != null)
            return user
        else
            throw UserNotFoundException(id)
    }

    override fun updateUser(newUser: User, id: Long): User? {
        return when(val user = userRepository.findById(id)) {
            null -> {
                newUser.id = id
                return userRepository.save(newUser)
            }
            else -> {
                user.firstName = newUser.firstName
                user.lastName = newUser.lastName
                return userRepository.save(user)
            }
        }
    }

    override fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }
}
