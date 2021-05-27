package com.example.yolla_backend.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookmarkForm {
    private String title;
    private int starttime;
    private int endtime;
    private String url;
    private String username;
}
