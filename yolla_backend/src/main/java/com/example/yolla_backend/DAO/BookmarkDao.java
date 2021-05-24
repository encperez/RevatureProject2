package com.example.yolla_backend.DAO;

import com.example.yolla_backend.Model.Bookmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BookmarkDao {

    @Autowired
    private MongoTemplate template;

    public void clear () {
        template.dropCollection(Bookmark.class);
    }

    public Bookmark getBookmark (String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Bookmark targetBookmark = template.findOne(query, Bookmark.class);
        return targetBookmark;
    }

    public Bookmark putBookmark(Bookmark bookmark) {
        template.save(bookmark, "bookmarks");
        return bookmark;
    }

    public void removeBookmark(Bookmark bookmark) {
        template.remove(bookmark, "bookmarks");
    }
}
