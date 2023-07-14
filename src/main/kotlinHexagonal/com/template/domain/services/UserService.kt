package com.template.domain.services

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import com.template.domain.models.User
import com.template.exceptions.UserNotFoundException
import kotlin.collections.MutableList

/** Provide user related functionality.  */
interface UserService {
    fun getFullName(id: Long): String?
    fun findAll(): MutableList<User?>
    fun createNew(user: User): User?
    fun getUser(id: Long): User?
    fun updateUser(newUser: User, id: Long): User?
    fun deleteUser(id: Long)
}