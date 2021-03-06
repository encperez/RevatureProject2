package com.example.yolla_backend.Service;

import com.example.yolla_backend.DAO.BookmarkDao;
import com.example.yolla_backend.Model.Bookmark;
import com.example.yolla_backend.Model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class BookmarkService {

    @Autowired
    private BookmarkDao dao;

    public void clear() {
        dao.clear();
    }

    /**
     * Create a new bookmark and add it do the bookmark database
     * @param title title of the new bookmark
     * @param startTime start time of the new bookmark
     * @param endTime end time of the new bookmark
     * @param url url of the video for the bookmark
     * @param username username of the user creating the bookmark
     * @return true on success, false on failure
     */
    public boolean newBookmark(String title, int startTime, int endTime, String url, String username) {
        Bookmark newBookmark = new Bookmark();
        newBookmark.setTitle(title);
        newBookmark.setStartTime(startTime);
        newBookmark.setEndTime(endTime);
        newBookmark.setUrl(url);
        dao.putBookmark(newBookmark);

        log.info("New bookmark created");
        User user = dao.getUserByUsername(username);
        user.getBookmarks().add(newBookmark.getId());
        dao.updateUser(user);

        return true;
    }

    /**
     * Removes a bookmark from the database and from the current user
     * @param bookmarkId ID of the bookmark being removed
     * @param username username of the user removing the bookmark
     */
    public void removeBookmark(String bookmarkId, String username) {
        System.out.println("Check 0");
        Bookmark targetBookmark = dao.getBookmarkById(bookmarkId);
        System.out.println("Check 1");
        dao.removeBookmark(targetBookmark);

        User user = dao.getUserByUsername(username);
        System.out.println("Check 2");
        user.getBookmarks().remove(bookmarkId);
        dao.updateUser(user);
    }

    /**
     * Grabs a user's bookmarks
     * @param username user requesting their list of bookmarks
     * @return list of bookmarks
     */
    public Bookmark[] getBookmarks(String username) {
        User user = dao.getUserByUsername(username);
        List<String> bookmarks = user.getBookmarks();
        Bookmark[] bookmarkArray = new Bookmark[bookmarks.size()];

        for (int i = 0; i < bookmarks.size(); i++) {
            Bookmark tempBookmark = dao.getBookmarkById(bookmarks.get(i));
            bookmarkArray[i] = tempBookmark;
        }

        return bookmarkArray;
    }
}
