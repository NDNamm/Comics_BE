package com.example.comic_render.DTO;

import com.example.comic_render.Entity.EnumStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComicsDTO {

    private Long id;
    private String name;
    private String description;
    private String coverUrl;
    private String slug;
    private LocalDateTime createdAt;
    private String author;
    private EnumStatus status;
    private List<ChaptersDTO> chapters;
    private List<Long> genreId;
    private List<String> genreNames;
}
