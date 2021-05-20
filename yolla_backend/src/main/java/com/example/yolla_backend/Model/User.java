package com.example.yolla_backend.Model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data // @Getter + @Setter + @RequiredArgsConstructor + @ToString + @EqualsAndHashCode
@NoArgsConstructor
@Document(collection = "User")
/**
 *
 */
public class User {
    @Id
    @NonNull String id;
    @NonNull String username;
    @NonNull String password;
    @NonNull List<String> bookmarks;
    @NonNull Map<String, String> words; // key: word, value: reference bookmark id
}
