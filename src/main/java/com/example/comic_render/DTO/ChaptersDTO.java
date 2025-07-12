package com.example.comic_render.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChaptersDTO {

    private Long id;
    private String name;
    private String slug;
    private String content;
    private LocalDateTime createdAt;
    List<ImagesDTO> imagesUrl;

}
