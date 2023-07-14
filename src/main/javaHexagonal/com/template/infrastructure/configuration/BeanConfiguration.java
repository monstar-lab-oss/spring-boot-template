package com.template.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import com.template.domain.services.DomainUserService;
import com.template.domain.services.UserService;
import com.template.domain.repositories.UserRepository;
import com.template.MainApplication;

@Configuration
@ComponentScan(basePackageClasses = MainApplication.class)
public class BeanConfiguration {

    @Bean
    UserService userService(UserRepository userRepository) {
        return new DomainUserService(userRepository);
    }
}