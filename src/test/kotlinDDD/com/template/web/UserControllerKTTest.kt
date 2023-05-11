//package com.template.web
//
//import org.mockito.ArgumentMatchers.any
//import org.mockito.Mockito.*
//import org.mockito.MockitoAnnotations.openMocks
//import java.util.Optional
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.mockito.Mock
//import com.template.models.User
//import com.template.services.UserService
//import com.template.repositories.UserRepository
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.test.context.junit4.SpringRunner
//import org.springframework.test.context.ActiveProfiles
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//class UserControllerKTTest(@Autowired val mockMvc: MockMvc) {
//  @Mock lateinit var userService: UserService
//  @Mock lateinit var userRepository: UserRepository
//
//  @BeforeEach
//  fun setUp(): Unit {
//    openMocks(this)
//    userService = spy(UserService(userRepository))
//  }
//
//  @Test
//  fun test_get_all(): Unit {
//    var existingUser = User("First", "Last", 1)
//    `when`(userService.findAll())
//            .thenReturn(listOf(existingUser))
//
//    mockMvc.perform(get("/users"))
//            .andExpect(status().isOk)
//            .andExpect(jsonPath("$").isArray());
//  }
//}
