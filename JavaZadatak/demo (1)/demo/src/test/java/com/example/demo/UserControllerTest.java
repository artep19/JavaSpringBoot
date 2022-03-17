package com.example.demo;

import com.example.demo.model.BaseUser;
import com.example.demo.model.User;
import com.example.demo.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    UserServiceImpl userService;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> usersList = new ArrayList<>();
        User user = getUser();
        usersList.add(user);
        Mockito.when(userService.getUsers()).thenReturn(usersList);
        mockMvc.perform(get("/user/getAll")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].username", Matchers.equalTo("petracrmaric")));
    }

    @Test
    public void testGetUserById() throws Exception {
        Mockito.when(userService.findById(getUser().getId())).thenReturn(getUser());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", Matchers.equalTo("petracrmaric")));
    }

    @Test
    public void testCreateNewUser() throws Exception {
        BaseUser user = new BaseUser("pc", "123456789");
        mockMvc.perform(post("/user/create")
                        .content(asJson(user))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    private static ObjectMapper mapper = new ObjectMapper();

    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("petracrmaric");
        user.setPassword("abcdefg");
        return user;
    }

    private static String asJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
