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
/**
 *
 */
public class UserController {

    @Autowired
    private UserService service;

    /**
     *
     */
    public void clear() { service.clear(); }

    /**
     *
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
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/user/{id}")
    public ResponseEntity GetUser(@PathVariable String id) {
        log.info("GetUser: " + id);
        return ResponseEntity.ok(service.GetUserById(id));
    }

    /**
     *
     * @param username
     * @return
     */
    @GetMapping("/user/{username}/words")
    public ResponseEntity GetWords(@PathVariable String username) {
        return ResponseEntity.ok(service.GetWords(username));
    }

    /**
     *
     * @param username
     * @param word
     * @return
     */
    @PutMapping("/word/{username}/{word}")
    public ResponseEntity AddWord(@PathVariable String username, @PathVariable String word) {
        service.AddWord(username, word);
        return ResponseEntity.ok(service.GetWords(username));
    }

    /**
     *
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
