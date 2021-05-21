package com.example.yolla_backend.Service;

import com.example.yolla_backend.DAO.BookmarkDao;
import com.example.yolla_backend.Model.Bookmark;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

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
}
