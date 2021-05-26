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
     *
     * @param username
     * @param password
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
     *
     * @param username
     * @return returned user
     */
    public User GetUser(String username) {
        return dao.GetUser(username);
    }

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
     *
     * @param id
     * @return
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
     *
     * @param id
     * @param word
     */
    public void AddWord(String id, String word){
        User u = dao.GetUserById(id);
        u.getWords().put(word, null);
        dao.PutUser(u);
    }

    /**
     *
     * @param id
     * @param word
     */
    public void RemoveWord(String id, String word){
        User u = dao.GetUserById(id);
        u.getWords().remove(word);
        dao.PutUser(u);
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public User LoginUser(String username, String password) {
        User u = dao.GetUser(username);
        return (u != null && u.getPassword().equals(password)) ? u : null;
    }
}
