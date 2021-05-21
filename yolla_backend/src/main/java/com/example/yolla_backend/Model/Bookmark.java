package com.example.yolla_backend.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "Bookmarks")
public class Bookmark {
    @Id
    @NonNull String id;
    @NonNull String title;
    @NonNull int startTime;
    @NonNull int endTime;
    @NonNull String url;
}
