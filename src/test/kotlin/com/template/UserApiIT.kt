package com.template

import com.template.models.User
import com.template.repositories.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(classes = [MainApplication::class])
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class UserApiIT {

    @Autowired
    var userRepository: UserRepository? = null

    @Autowired
    var mockMvc: MockMvc? = null

    @Test
    fun `find Users`() {
        val user = User("Name", "Lastname")
        userRepository?.save(user)

        mockMvc!!.perform(
            MockMvcRequestBuilders.get("/users/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect { result: MvcResult ->
                    val content = result.response.contentAsString
                    assertThat(content).contains("Lastname")}
                .andReturn()
    }
}