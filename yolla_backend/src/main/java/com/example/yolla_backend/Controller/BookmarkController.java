package com.example.yolla_backend.Controller;

import com.example.yolla_backend.Model.BookmarkForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.log4j.Log4j2;
import com.example.yolla_backend.Model.Bookmark;
import com.example.yolla_backend.Service.BookmarkService;

@Log4j2
@RestController
@CrossOrigin(origins = "http://localhost:3000")
/**
 * Controller class for request mapping for bookmark services
 */
public class BookmarkController {

    @Autowired
    private BookmarkService service;

    /**
     * Clears contents of the database
     */
    public void clear() { service.clear(); }

    /**
     * Controller call to bookmark service to construct a new bookmark and add it to the current user
     */
    @PostMapping(path = "/newBookmark")
    public ResponseEntity newBookmark(@RequestBody BookmarkForm form) {
        log.info("New Bookmark:" + form.getUsername());
        boolean result = service.newBookmark(form.getTitle(), form.getStarttime(), form.getEndtime(), form.getUrl(), form.getUsername());
        return ResponseEntity.ok(result);
    }

    /**
     * Controller call to bookmark service to remove a bookmark from the database and the current user's list of bookmarks
     */
    @DeleteMapping("/bookmark/{userid}/{bookmarkid}")
    public ResponseEntity removeBookmark(@PathVariable String bookmarkid, @PathVariable String userid) {
        log.info("Bookmark removed:" + bookmarkid);
        service.removeBookmark(bookmarkid, userid);
        return ResponseEntity.ok(service.getBookmarks(userid));
    }

    /**
     * Controller call to bookmark service to get the list of bookmarks for a specified user
     */
    @GetMapping("/bookmark/{userid}")
    public ResponseEntity getBookmarks(@PathVariable String userid) {
        Bookmark[] bookmarks = service.getBookmarks(userid);
        return ResponseEntity.ok(bookmarks);
    }

}
