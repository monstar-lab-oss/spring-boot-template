package com.template.web;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.mockito.Mockito.spy;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.template.models.User;
import java.util.List;
import com.template.services.UserService;
import com.template.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManagerAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD;
import org.springframework.test.context.jdbc.SqlMergeMode;
import static org.springframework.test.context.jdbc.SqlMergeMode.MergeMode.MERGE;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import com.template.exceptions.UserNotFoundException;
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@EnableAutoConfiguration
@AutoConfigureTestDatabase
@Import(TestEntityManagerAutoConfiguration.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class UserControllerTest {
  @Mock private UserRepository userRepository;

  @Mock private UserService userService;

  @Autowired
  protected MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    openMocks(this);
    userService = spy(new UserService(userRepository));
  }

  @Test
  @DirtiesContext(methodMode = AFTER_METHOD)
  @Sql(
        scripts = {
                "classpath:scripts/create_user.sql"
        })
  public void test_get_all() throws Exception {
    this.mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.*", hasSize(1)))
            .andExpect(jsonPath("$.*.firstName").value(anyOf(hasItem("Firstname"))))
            .andExpect(jsonPath("$.*.lastName").value(anyOf(hasItem("Lastname"))));
  }

  @Test
  @DirtiesContext(methodMode = AFTER_METHOD)
  @SqlMergeMode(MERGE)
  public void test_create_user() throws Exception {
    var request =
            "{\"firstName\":\"name\", \"lastName\":\"surname\", \"id\":\"2\"}";

    this.mockMvc.perform(post("/users")
            .content(request)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("name"))
            .andExpect(jsonPath("$.lastName").value("surname"));
  }

  @Test
  @DirtiesContext(methodMode = AFTER_METHOD)
  @SqlMergeMode(MERGE)
  public void test_create_user_bad_request() throws Exception {
    var request =
            "[{\"first_name\":\"name\"}]";

    this.mockMvc.perform(post("/users")
            .content(request)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
  }

  @Test
  @DirtiesContext(methodMode = AFTER_METHOD)
  @Sql(
          scripts = {
                  "classpath:scripts/create_user.sql"
          })
  public void test_get_one() throws Exception {
    this.mockMvc.perform(get("/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Firstname"))
            .andExpect(jsonPath("$.lastName").value("Lastname"));
  }

  @Test
  @DirtiesContext(methodMode = AFTER_METHOD)
  public void test_get_one_doesnt_exist() throws Exception {
    Assertions.assertThrows(Exception.class, () -> this.mockMvc.perform(get("/users/1")));
  }

  @Test
  @DirtiesContext(methodMode = AFTER_METHOD)
  @Sql(
          scripts = {
                  "classpath:scripts/create_user.sql"
          })
  public void test_update_existent_user() throws Exception {
    var request =
            "{\"firstName\":\"name\", \"lastName\":\"surname\"}";

    this.mockMvc.perform(put("/users/1")
            .content(request)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("name"))
            .andExpect(jsonPath("$.lastName").value("surname"));
  }

  @Test
  @DirtiesContext(methodMode = AFTER_METHOD)
  public void test_update_not_existent_user() throws Exception {
    var request =
            "{\"firstName\":\"name\", \"lastName\":\"surname\", \"id\":\"1\"}";

    this.mockMvc.perform(put("/users/1")
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("name"))
            .andExpect(jsonPath("$.lastName").value("surname"));
  }

  @Test
  @DirtiesContext(methodMode = AFTER_METHOD)
  @Sql(
          scripts = {
                  "classpath:scripts/create_user.sql"
          })
  public void test_delete_user() throws Exception {
    this.mockMvc.perform(delete("/users/1"))
            .andExpect(status().isOk());
  }
}
