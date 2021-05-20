package com.example.yolla_backend.DAO;

import com.example.yolla_backend.Model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;

@Slf4j // lombok logging via log
@Repository
/**
 *
 */
public class UserRepo {

    @Autowired
    private MongoTemplate template;

    public void clear() {
        template.dropCollection(User.class);
    }

    /**
     *
     * @param username
     * @return
     */
    public User GetUser(String username){
        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    public User GetUserById(String id){
        return null;
    }

    /**
     *
     * @param u
     * @return
     */
    public User PutUser(User u) {
        template.save(u);
        return u;
    }
}
