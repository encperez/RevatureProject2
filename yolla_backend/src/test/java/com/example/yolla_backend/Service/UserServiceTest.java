package com.example.yolla_backend.Service;

import com.example.yolla_backend.Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        boolean success = service.NewUser("g", "s");
        assertTrue(success);
    }

    @Test
    void getUser() {
    }

    @Test
    void getBookmarks() {
    }

    @Test
    void getBookmark() {
    }

    @Test
    void getWords() {
    }

    @Test
    void getWord() {
    }

    @Test
    void removeBookMark() {
    }

    @Test
    void addWord() {
    }

    @Test
    void removeWord() {
    }
}