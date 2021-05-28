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

    /**
     * Clears database
     */
    public void clear () {
        template.dropCollection("bookmarks");
        template.dropCollection("users");
    }

    /**
     * Grab a bookmark from the database using the id of the bookmark
     * @param id
     * @return
     */
    public Bookmark getBookmarkById (String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Bookmark targetBookmark = template.findOne(query, Bookmark.class, "bookmarks");
        return targetBookmark;
    }

    /**
     * Grab a bookmark from the database based on the title
     * @param title
     * @return
     */
    public Bookmark getBookmarkByTitle (String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));
        Bookmark targetBookmark = template.findOne(query, Bookmark.class, "bookmarks");
        return targetBookmark;
    }

    /**
     * Grab a user from the database based on username
     * @param username username of user being searched for
     * @return target user object
     */
    public User getUserByUsername(String username){
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        User user = template.findOne(query, User.class);
        return user;
    }

    /**
     * Saves user to database
     * @param u user being saved
     * @return user being saved
     */
    public User updateUser(User u) {
        template.save(u);
        return u;
    }

    /**
     * Saves a bookmark to database
     * @param bookmark bookmark being saved
     * @return bookmark being saved
     */
    public Bookmark putBookmark(Bookmark bookmark) {
        template.save(bookmark, "bookmarks");
        return bookmark;
    }

    /**
     * Removes a bookmark from the database
     * @param bookmark bookmark being removed
     */
    public void removeBookmark(Bookmark bookmark) {
        template.remove(bookmark, "bookmarks");
    }
}
