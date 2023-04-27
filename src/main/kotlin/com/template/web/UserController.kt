package com.template.web

import kotlin.collections.List
import com.template.services.UserService
import com.template.models.User
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import lombok.RequiredArgsConstructor
import org.springframework.http.MediaType
import java.util.Optional
import com.template.exceptions.UserNotFoundException

@RestController
@RequiredArgsConstructor
internal class UserController(val service: UserService) {
    @GetMapping("/users")
    fun all(): Iterable<User> {
        return service.findAll()
    }

    @PostMapping("/users")
    fun newUser(@RequestBody newUser: User): User {
        return service.createNew(newUser)
    }

    @GetMapping("/users/{id}")
    fun one(@PathVariable id: Long): User? {
        return service.getUser(id) ?: throw UserNotFoundException(id)
    }

    @PutMapping("/users/{id}")
    fun updateUser(@RequestBody newUser: User, @PathVariable id: Long): User {
        return service.updateUser(newUser, id)
    }

    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Long) {
        service.deleteUser(id)
    }
}