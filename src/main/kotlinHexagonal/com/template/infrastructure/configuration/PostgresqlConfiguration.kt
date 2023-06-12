package com.template.infrastructure.configuration

import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import com.template.infrastructure.repositories.JpaUserRepository

@EnableJpaRepositories(basePackages = arrayOf("com.template.infrastructure.repositories"))
class PostgresqlConfiguration  