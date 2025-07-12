package com.example.comic_render.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "comics")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "slug")
    private String slug;

    @Column(name = "author")
    private String author;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EnumStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "comic",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Chapters> chapters;

    @ManyToMany
    @JoinTable(
            name = "comic_genres",
            joinColumns = @JoinColumn(name = "comic_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genres> genres = new HashSet<>();

}
