package com.template

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.beans.factory.annotation.Autowired

@DataJpaTest
@ActiveProfiles("test")
public abstract class IntegrationRepositoryKTTests {
	 @Autowired
    lateinit var entityManager: TestEntityManager
}
