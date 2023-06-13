package com.template.infrastructure.repositories

import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository
import com.template.domain.models.User
import kotlin.collections.MutableList
import java.util.Optional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import com.template.domain.repositories.UserRepository

@Component
@Primary
class PostgresqlDBUserRepository @Autowired constructor(userRepository: JpaUserRepository) : UserRepository {
    private val userRepository: JpaUserRepository

    init {
        this.userRepository = userRepository
    }

    override fun findById(id: Long): User? {
        return when(val user = userRepository.findById(id)) {
            Optional.ofNullable(null) -> {
                return null
            }
            else -> {
                return user.get()
            }
        }
    }

    override fun save(user: User): User? {
        return userRepository.save(user)
    }

    override fun findAll(): MutableList<User?> {
        return userRepository.findAll()
    }

    override fun deleteById(id: Long) {
        userRepository.deleteById(id)
    }
}
