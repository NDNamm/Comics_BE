package com.example.comic_render.DTO;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDTO {

    private Long id;
    private String comment;
    private LocalDateTime createdAt;
}
