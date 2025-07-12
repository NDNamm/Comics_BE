package com.example.comic_render.Repository;

import com.example.comic_render.Entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
}
