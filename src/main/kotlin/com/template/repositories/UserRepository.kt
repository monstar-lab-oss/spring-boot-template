package com.template.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import com.template.models.User
 
@Repository
interface UserRepository : CrudRepository<User, Long> {
}