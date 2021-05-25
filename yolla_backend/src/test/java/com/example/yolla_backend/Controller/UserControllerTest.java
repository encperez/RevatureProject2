package com.example.yolla_backend.Controller;

import com.example.yolla_backend.DAO.UserRepo;
import com.example.yolla_backend.Model.LoginForm;
import com.example.yolla_backend.Model.User;
import com.example.yolla_backend.Service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    // make mock calls
    MockMvc mockMvc;

    @MockBean
    UserService service; // autowired with controller with @webmvctest

    @Mock
    UserRepo userdao;

    @Test
    void clear() {
    }

    private String userJson(String username, String password) {
        return "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";
    }

    @Test
    void newUser() throws Exception {
        String username = "test123";
        String password = "pass";
        when(service.NewUser(username, password)).thenReturn(true);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(userJson(username, password));

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("true"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getUser() throws Exception {
        User u = userdao.GetUser("gene");

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/user").param("id", u.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("true"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getWords() {
    }

    @Test
    void addWord() {
    }

    @Test
    void removeWords() {
    }
}