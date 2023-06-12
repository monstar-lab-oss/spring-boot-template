package com.template.exceptions

class UserNotFoundException(id: Long?) : RuntimeException("Could not find user $id") 