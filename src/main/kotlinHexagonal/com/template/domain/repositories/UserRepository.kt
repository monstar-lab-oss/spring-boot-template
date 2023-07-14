package com.template.domain.repositories

import java.util.Optional
import com.template.domain.models.User
import kotlin.collections.MutableList

interface UserRepository {
    fun findById(id: Long): User?
    fun save(user: User): User?
    fun findAll(): MutableList<User?>
    fun deleteById(id: Long)
}