package com.example.comic_render.Repository;

import com.example.comic_render.Entity.Chapters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChapterRepository extends JpaRepository<Chapters, Long> {
   Optional<Chapters> findBySlug(String slug);

   @Query("select c from Chapters c where lower(c.slug) Like lower(concat('%', :slug, '%') ) ")
   List<Chapters> findBySlugLike(String slug);

   List<Chapters> deleteChaptersByComicId(Long comicId);
}
