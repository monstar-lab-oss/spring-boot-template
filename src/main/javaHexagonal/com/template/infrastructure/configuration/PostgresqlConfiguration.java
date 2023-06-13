package com.template.infrastructure.configuration;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.template.infrastructure.repositories.JpaUserRepository;
@EnableJpaRepositories(basePackageClasses = JpaUserRepository.class)
public class PostgresqlConfiguration {
}