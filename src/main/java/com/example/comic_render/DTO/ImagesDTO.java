package com.example.comic_render.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImagesDTO {

    private Long id;
    private String imageUrl;
    private String publicId;
    private int size;
}
