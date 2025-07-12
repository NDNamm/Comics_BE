package com.example.comic_render.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenresDTO {
    private Long id;
    private String genreName;
}
