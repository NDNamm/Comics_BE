package com.example.comic_render.service;

import com.example.comic_render.DTO.ComicsDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ComicsService {

    Page<ComicsDTO> getAllComics(int page, int size);

    ComicsDTO getComicBySlug(String slug);

    void addComics(ComicsDTO comicsDTO, MultipartFile file);

    void updateComics(ComicsDTO comicsDTO, Long id, MultipartFile file);

    void deleteComics(Long id);

    Page<ComicsDTO> searchAllComics(String keyword, int page, int size);

    Page<ComicsDTO> searchComicsByGenres(List<Long> genreId, int page, int size);
}
