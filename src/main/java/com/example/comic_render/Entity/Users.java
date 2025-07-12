package com.example.comic_render.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "role_id")
    private Roles roles;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Comments> comments;
}
