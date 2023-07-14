package com.template.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ComponentScan
import com.template.domain.services.DomainUserService
import com.template.domain.services.UserService
import com.template.domain.repositories.UserRepository
import com.template.MainApplication

@Configuration
@ComponentScan(basePackages = arrayOf("com.template"))
class BeanConfiguration {
    @Bean
    fun userService(userRepository: UserRepository): UserService? {
        return DomainUserService(userRepository)
    }
}