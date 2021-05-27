package com.example.yolla_backend.DAO;

import com.example.yolla_backend.Model.Bookmark;
import com.example.yolla_backend.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookmarkDao {

    @Autowired
    private MongoTemplate template;

    public void clear () {
        template.dropCollection("bookmarks");
        template.dropCollection("users");
    }

    public Bookmark getBookmarkById (String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Bookmark targetBookmark = template.findOne(query, Bookmark.class, "bookmarks");
        return targetBookmark;
    }

    public Bookmark getBookmarkByTitle (String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));
        Bookmark targetBookmark = template.findOne(query, Bookmark.class, "bookmarks");
        return targetBookmark;
    }

    public User getUserByUsername(String username){
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        User user = template.findOne(query, User.class);
        return user;
    }

    public User updateUser(User u) {
        template.save(u);
        return u;
    }

    public Bookmark putBookmark(Bookmark bookmark) {
        template.save(bookmark, "bookmarks");
        return bookmark;
    }

    public void removeBookmark(Bookmark bookmark) {
        template.remove(bookmark, "bookmarks");
    }
}
