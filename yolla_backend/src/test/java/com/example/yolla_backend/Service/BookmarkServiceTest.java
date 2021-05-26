package com.example.yolla_backend.Service;

import com.example.yolla_backend.DAO.BookmarkDao;
import com.example.yolla_backend.DAO.UserRepo;
import com.example.yolla_backend.Model.Bookmark;
import com.example.yolla_backend.Model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookmarkServiceTest {

    @Autowired
    private BookmarkService service;

    @Autowired
    private BookmarkDao bookmarkDao;

    @AfterEach
    void setUp() {
        service.clear();
    }

    @Test
    void newBookmarkTest() {
        User user = new User("Michael", "Fong");
        bookmarkDao.updateUser(user);
        boolean success = service.newBookmark("test" , 0, 0, "test", "Michael");
        assertTrue(success);
    }

    @Test
    void removeBookmarkTest() {
        newBookmarkTest();
        service.removeBookmark(bookmarkDao.getBookmarkByTitle("test").getId(), "Michael");
        assertEquals(null, bookmarkDao.getBookmarkByTitle("test"));
    }

    @Test
    void getBookmarksTest() {
        newBookmarkTest();
        Bookmark[] bookmarks = service.getBookmarks("Michael");
        assertNotEquals(0, bookmarks.length);
        service.clear();
    }
}
