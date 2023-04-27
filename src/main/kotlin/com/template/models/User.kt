package com.template.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.*

@Data
@Entity
@Table(name = "users")
class User(
	var firstName: String?,
	var lastName: String?,
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = -1
) {
		private constructor() : this("", "")
	}