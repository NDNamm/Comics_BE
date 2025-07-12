package com.example.comic_render.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "genres")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String genreName;

    @ManyToMany(mappedBy = "genres")
    private List<Comics> comics = new ArrayList<>();
}
