package com.example.yolla_backend.DAO;

import com.example.yolla_backend.Model.User;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.List;

@Log4j2 // lombok logging via log
@Repository
/**
 *
 */
public class UserRepo {

    @Autowired
    private MongoTemplate template;

    /**
     *
     */
    public void clear() {
        template.dropCollection(User.class);
    }

    /**
     *
     * @param username
     * @return
     */
    public User GetUser(String username){
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        List<User> users = template.find(query, User.class);
        return users.size() > 0 ? users.get(0) : null;
    }

    /**
     *
     * @param id
     * @return
     */
    public User GetUserById(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        List<User> users = template.find(query, User.class);
        return users.size() > 0 ? users.get(0) : null;
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
