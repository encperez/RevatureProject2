package com.example.yolla_backend.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Getter + @Setter + @RequiredArgsConstructor + @ToString + @EqualsAndHashCode
@NoArgsConstructor
public class LoginForm {
    private String username;
    private String password;
}
