package com.example.comic_render.Repository;

import com.example.comic_render.Entity.Comics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComicRepository extends JpaRepository<Comics, Long> {
    Optional<Comics> findBySlug(String slug);

    @Query("select c from Comics c where lower(c.slug) like lower(concat('%', :slug, '%') ) ")
    Page<Comics> findBySlugContaining(String slug, Pageable pageable);

    @Query("select c from Comics c where lower(c.name) like lower(concat('%', :keyword, '%') )" +
            "OR LOWER(c.slug) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
    Page<Comics> findByNameOrSlug(String keyword, Pageable pageable);

    @Query("select c from Comics c " +
            "join c.genres tl where  tl.id in :genreId " +
            "group by c having count (distinct  tl.id) = :sl")
    Page<Comics> findComicsByGenres(@Param("genreId") List<Long> genreId ,@Param("sl") Long sl, Pageable pageable);
}
