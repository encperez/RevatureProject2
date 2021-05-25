package com.example.yolla_backend.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.log4j.Log4j2;
import com.example.yolla_backend.Model.Bookmark;
import com.example.yolla_backend.Service.BookmarkService;

@Log4j2
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class BookmarkController {

    @Autowired
    private BookmarkService service;

    public void clear() { service.clear(); }

    @PostMapping("/bookmark/{userid}/{title}")
    public ResponseEntity newBookmark(@PathVariable String title, @PathVariable int startTime, @PathVariable int endTime, @PathVariable String url, @PathVariable String userid) {
        log.info("New Bookmark:" + title);
        boolean result = service.newBookmark(title, startTime, endTime, url, userid);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/bookmark/{userid}/{bookmarkid}")
    public ResponseEntity removeBookmark(@PathVariable String bookmarkid, @PathVariable String userid) {
        log.info("Bookmark removed:" + bookmarkid);
        service.removeBookmark(bookmarkid, userid);
        return ResponseEntity.ok(service.getBookmarks(userid));
    }

    @GetMapping("/bookmark/{userid}")
    public ResponseEntity getBookmarks(@PathVariable String userid) {
        Bookmark[] bookmarks = service.getBookmarks(userid);
        return ResponseEntity.ok(bookmarks);
    }

}
