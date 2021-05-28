package com.example.yolla_backend.Controller;

import com.example.yolla_backend.Model.LoginForm;
import com.example.yolla_backend.Model.User;
import com.example.yolla_backend.Service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.List;

@Log4j2
@RestController
@CrossOrigin(origins = "http://localhost:3000")
/**
 *
 */
public class UserController {

    @Autowired
    private UserService service;

    /**
     * Clears contents of the database
     */
    public void clear() { service.clear(); }

    /**
     *  Greeting page
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "Greetings from Yolla Back!";
    }

    /**
     * put user
     * @param form
     * @return
     */
    @PostMapping(path = "/register", consumes = "application/json")
    public ResponseEntity NewUser(@RequestBody LoginForm form) {
        log.info("NewUser: " + form.getUsername());
        boolean result = service.NewUser(form.getUsername(), form.getPassword());
        return ResponseEntity.ok(result);
    }

    /**
     * put user
     * @param form
     * @return
     */

    @PostMapping(path = "/login")
    public ResponseEntity LoginUser(@RequestBody LoginForm form) {
        log.info("login: " + form.getUsername());
        User u = service.LoginUser(form.getUsername(), form.getPassword());
        return ResponseEntity.ok(u == null ? null : u.getId());
    }

    /**
     * Get user
     * @param id
     * @return
     */
    @GetMapping(path = "/user/{id}")
    public ResponseEntity GetUser(@PathVariable String id) {
        log.info("GetUser: " + id);
        User u = service.GetUserById(id);
        return ResponseEntity.ok(u);
    }

    /**
     * Get words for a user
     * @param username
     * @return
     */
    @GetMapping("/user/{username}/words")
    public ResponseEntity GetWords(@PathVariable String username) {
        return ResponseEntity.ok(service.GetWords(username));
    }

    /**
     * Add a word to the user's list of words
     * @param id
     * @param word
     * @return
     */
    @PutMapping("/word/{id}/{word}")
    public ResponseEntity AddWord(@PathVariable String id, @PathVariable String word) {
        System.out.println("here" + id + word);
        service.AddWord(id, word);
        return ResponseEntity.ok(service.GetWords(id));
    }

    /**
     * Remove a word from the user's list of words
     * @param username
     * @param word
     * @return
     */
    @DeleteMapping("/word/{username}/{word}")
    public ResponseEntity RemoveWord(@PathVariable String username, @PathVariable String word) {
        service.RemoveWord(username, word);
        return ResponseEntity.ok(service.GetWords(username));
    }
}
