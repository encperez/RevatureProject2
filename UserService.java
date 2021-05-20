package com.example.yolla_backend.Service;

import com.example.yolla_backend.DAO.UserRepo;
import com.example.yolla_backend.Model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@Slf4j // lombok logging via log
@Component
/**
 *
 */
public class UserService {

    @Autowired
    private UserRepo dao;

    public void clear() {
        dao.clear();
    }

    /**
     *
     * @param username
     * @param password
     * @return true if user is created
     */
    boolean NewUser(String username, String password) {
        User u = dao.GetUser(username);
        boolean success = false;
        if(u == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            dao.PutUser(newUser);
            success = true;
        }
        log.info(MessageFormat.format("NewUser({0},{1}) {2}", username, password, success ? "Success" : "Fail"));
        return success;
    }

    /**
     *
     * @param username
     * @param password
     * @return returned user
     */
    User GetUser(String username) {
        return dao.GetUser(username);
    }

    User GetUserById(String id) {
        return dao.GetUserById(id);
    }

    /**
     *
     * @param id
     * @return
     */
    List<String> GetBookmarks(String id) {
       return null;
    }

    /**
     * Calls get GetBookmarks
     * @param id
     * @return
     */
    List<String> GetBookmark(String id) {
        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    Map<String, String> GetWords(String id) {
        return null;
    }

    /**
     * calls GetWords
     * @param id
     * @return
     */
    Map<String, String> GetWord(String id) {
        return null;
    }

    /**
     *
     * @param b
     */
//    void AddBookMark(BookMark b) {}

    /**
     *
     * @param id
     */
    void RemoveBookMark(String id){}

    /**
     *
     * @param w
     */
    void AddWord(String word){}

    /**
     *
     * @param word
     */
    void RemoveWord(String word){}
}
