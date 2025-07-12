package com.example.comic_render.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "roles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rolename")
    private String roleName;

    @OneToMany(mappedBy = "roles")
    @JsonIgnore
    private List<Users> users;
}
