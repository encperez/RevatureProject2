package com.example.yolla_backend.Service;

import com.example.yolla_backend.DAO.BookmarkDao;
import com.example.yolla_backend.Model.Bookmark;
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

    boolean newBookmark(String title, int startTime, int endTime, String url) {
        Bookmark newBookmark = new Bookmark();
        newBookmark.setTitle(title);
        newBookmark.setStartTime(startTime);
        newBookmark.setEndTime(endTime);
        newBookmark.setUrl(url);
        dao.putBookmark(newBookmark);

        log.info("New bookmark created");

        return true;
    }

    void removeBookmark(String id) {
        Bookmark targetBookmark = dao.getBookmark(id);
        dao.removeBookmark(targetBookmark);
    }

    Bookmark[] getBookmarks(List<String> bookmarks) {
        Bookmark[] bookmarkArray = new Bookmark[bookmarks.size()];

        for (int i = 0; i < bookmarks.size(); i++) {
            Bookmark tempBookmark = dao.getBookmark(bookmarks.get(i));
            bookmarkArray[i] = tempBookmark;
        }

        return bookmarkArray;
    }
}
