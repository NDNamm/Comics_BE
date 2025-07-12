package com.example.comic_render.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "images")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "public_id")
    private String publicId;

    @Column(name = "size")
    private Long size;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "chapter_id")
    private Chapters chapter;
}
