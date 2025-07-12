package com.example.comic_render.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "chapters")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chapters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "slug")
    private String slug;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "comic_id")
    private Comics comic;

    @OneToMany(mappedBy = "chapter")
    @JsonIgnore
    private List<Comments> comments;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Images> images;
}
