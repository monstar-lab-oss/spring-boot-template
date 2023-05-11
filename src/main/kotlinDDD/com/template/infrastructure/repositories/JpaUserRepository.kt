package com.template.infrastructure.repositories

import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository
import com.template.domain.models.User

@Repository
interface JpaUserRepository : JpaRepository<User?, Long?> 