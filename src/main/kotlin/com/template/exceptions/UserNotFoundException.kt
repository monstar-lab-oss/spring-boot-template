package com.template.exceptions

import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

class UserNotFoundException(id: Long) : RuntimeException("Could not find user $id")
