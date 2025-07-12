package com.example.comic_render.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
}
