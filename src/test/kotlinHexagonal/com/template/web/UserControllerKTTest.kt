package com.template.web

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.openMocks
import java.util.Optional
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import com.template.domain.models.User
import com.template.domain.services.DomainUserService
import com.template.domain.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.ActiveProfiles
import org.springframework.context.annotation.Import
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManagerAutoConfiguration
import org.springframework.test.context.jdbc.Sql
import org.hamcrest.Matchers.*
import org.springframework.test.context.jdbc.SqlMergeMode
import org.springframework.test.context.jdbc.SqlMergeMode.MergeMode.MERGE
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD
import org.springframework.test.annotation.DirtiesContext.*
import org.springframework.http.MediaType


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@EnableAutoConfiguration
@AutoConfigureTestDatabase
@Import(TestEntityManagerAutoConfiguration::class)
class UserControllerKTTest(@Autowired val mockMvc: MockMvc) {
  @Mock lateinit var userService: DomainUserService
  @Mock lateinit var userRepository: UserRepository

  @BeforeEach
  fun setUp(): Unit {
    openMocks(this)
  }

  @Test
  @Sql(scripts = ["classpath:scripts/create_user.sql"])
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  fun test_get_all(): Unit {
    mockMvc.perform(get("/users"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.*", hasSize<String>(1)))
            .andExpect(jsonPath("$.*.firstName").value(anyOf(hasItem("Firstname"))))
            .andExpect(jsonPath("$.*.lastName").value(anyOf(hasItem("Lastname"))))
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
  @Throws(java.lang.Exception::class)
  fun test_create_user() {
    val request = "{\"firstName\":\"name\", \"lastName\":\"surname\", \"id\":\"2\"}"
    mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .content(request)
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .accept(org.springframework.http.MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("name"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("surname"))
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
  @Throws(java.lang.Exception::class)
  fun test_create_user_bad_request() {
    val request = "[{\"first_name\":\"name\"}]"
    mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .content(request)
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .accept(org.springframework.http.MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  @Sql(scripts = ["classpath:scripts/create_user.sql"])
  @Throws(java.lang.Exception::class)
  fun test_get_one() {
    mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Firstname"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Lastname"))
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  @Throws(Exception::class)
  fun test_get_one_doesnt_exist() {
    org.junit.jupiter.api.Assertions.assertThrows<Exception>(Exception::class.java, org.junit.jupiter.api.function.Executable { mockMvc.perform(MockMvcRequestBuilders.get("/users/1")) })
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  @Sql(scripts = ["classpath:scripts/create_user.sql"])
  @Throws(Exception::class)
  fun test_update_existent_user() {
    val request = "{\"firstName\":\"name\", \"lastName\":\"surname\"}"
    mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
            .content(request)
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .accept(org.springframework.http.MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("name"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("surname"))
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  @Throws(Exception::class)
  fun test_update_not_existent_user() {
    val request = "{\"firstName\":\"name\", \"lastName\":\"surname\", \"id\":\"1\"}"
    mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
            .content(request)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("name"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("surname"))
  }

  @Test
  @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
  @Sql(scripts = ["classpath:scripts/create_user.sql"])
  @Throws(Exception::class)
  fun test_delete_user() {
    mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
  }
}
