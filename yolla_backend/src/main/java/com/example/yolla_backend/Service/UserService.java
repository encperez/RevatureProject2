package com.example.yolla_backend.Service;

import com.example.yolla_backend.DAO.UserRepo;
import com.example.yolla_backend.Model.User;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2 // lombok logging via log
@Service
/**
 *
 */
public class UserService {

    @Autowired
    private UserRepo dao;

    /**
     *
     */
    public void clear() {
        dao.clear();
    }

    /**
     * Creates a new user object and adds it to the database
     * @param username username for the new user
     * @param password password for the new user
     * @return true if user is created
     */
    public boolean NewUser(String username, String password) {
        User u = dao.GetUser(username);
        boolean success = false;
        if(u == null) {
            dao.PutUser(new User(username, password));
            success = true;
        }
        log.info(MessageFormat.format("NewUser({0},{1}) {2}", username, password, success ? "Success" : "Fail"));
        return success;
    }

    /**
     * Gets user object from database using username
     * @param username username of user
     * @return returned user
     */
    public User GetUser(String username) {
        return dao.GetUser(username);
    }

    /**
     * Gets user object from database using user ID
     * @param id ID of user
     * @return target user
     */
    public User GetUserById(String id) {
        return dao.GetUserById(id);
    }

    /**
     *
     * @param id
     * @return
     */
//    public List<String> GetBookmarks(String username, String id) {
//       return null;
//    }

    /**
     * Calls get GetBookmarks
     * @param id
     * @return
     */
//    public List<String> GetBookmark(String username, String id) {
//        return null;
//    }

    /**
     * Method to get the list of words from current user
     * @param id ID of current user
     * @return List of words
     */
    public List<String> GetWords(String id) {
        User u = dao.GetUserById(id);
        return u.getWords().keySet().stream().collect(Collectors.toList());
    }

    /**
     *
     * @param b
     */
//    public void AddBookMark(String username, BookMark b) {}

    /**
     *
     * @param id
     */
    //public void RemoveBookMark(String username, String id){}

    /**
     * Adds a word to the current user's word list
     * @param id ID of current user
     * @param word Word to add to the user's list
     */
    public void AddWord(String id, String word){
        User u = dao.GetUserById(id);
        u.getWords().put(word, null);
        dao.PutUser(u);
    }

    /**
     * Removes a word from the database and user's word list
     * @param id ID of current user
     * @param word Word being removed
     */
    public void RemoveWord(String id, String word){
        User u = dao.GetUserById(id);
        u.getWords().remove(word);
        dao.PutUser(u);
    }

    /**
     * Checks for the existence of a user account with the listed credentials
     * @param username username of account
     * @param password password of account
     * @return user object if one is found
     */
    public User LoginUser(String username, String password) {
        User u = dao.GetUser(username);
        return (u != null && u.getPassword().equals(password)) ? u : null;
    }
}
