package com.example.yolla_backend.Service;

import com.example.yolla_backend.Model.User;
import org.junit.jupiter.api.*;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService service;

    @BeforeEach
    void setUp() {
        service.clear();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void newUser() {
        boolean success = service.NewUser("gene", "password");
        assertTrue(success);
    }

    @Test
    void getUser() {
        String username = "gene";
        User u = new User(username, "password");
        newUser();
        User res = service.GetUser(username);
        assertTrue(new ReflectionEquals(res, "id").matches(u));
    }

    @Test
    void getUserById() {
        // create user with this name
        String username = "gene";
        newUser();
        User res = service.GetUser(username);
        User byId = service.GetUserById(res.getId());
        assertTrue(new ReflectionEquals(res).matches(byId));
    }

    @Test
    void getWords() {
        newUser();
        String username = "gene";
        User u = service.GetUser(username);

        List<String> words = new ArrayList<>(Arrays.asList("word", "list", "string"));
        for(String s : words) {
            service.AddWord(u.getId(), s);
        }


        List<String> res = service.GetWords(u.getId());
        assertTrue(new ReflectionEquals(res).matches(words));
    }

    @Test
    void addWord() {
        String username = "gene";
        String password = "password";
        service.NewUser(username, password);

        User u = service.GetUser(username);
        String word = "definition";
        service.AddWord(u.getId(), word);
        List<String> words = service.GetWords(u.getId());
        String result = words.get(words.size()-1);
        assertEquals(result, word);
    }

    @Test
    void removeWord() {
        newUser();
        addWord();
        String username = "gene";
        User u = service.GetUser(username);
        String word = "definition";
        service.RemoveWord(u.getId(), word);
        assertTrue(service.GetWords(u.getId()).isEmpty());
    }
}