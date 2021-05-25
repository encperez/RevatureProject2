package com.example.yolla_backend.Model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data // @Getter + @Setter + @RequiredArgsConstructor + @ToString + @EqualsAndHashCode
@RequiredArgsConstructor
@Document(collection = "User")
/**
 *
 */
public class User {
    @Id
    String id;
    @NonNull String username;
    @NonNull String password;
    List<String> bookmarks = new ArrayList<>();
    // key: word, value: reference bookmark id
    Map<String, String> words = new HashMap<>();
}
