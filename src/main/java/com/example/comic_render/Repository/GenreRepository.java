package com.example.comic_render.Repository;

import com.example.comic_render.Entity.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genres, Long> {

    Optional<Genres> findByGenreName(String genre);

    @Query("select c from Genres c where lower(c.genreName) like lower(concat('%', :name, '%') ) ")
    List<Genres> findByName(String name);
}
