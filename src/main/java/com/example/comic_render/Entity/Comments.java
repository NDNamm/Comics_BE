package com.example.comic_render.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "chapter_id")
    private Chapters chapter;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private Users user;
}
