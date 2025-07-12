package com.example.comic_render.service;

import com.example.comic_render.DTO.GenresDTO;

import java.util.List;

public interface GenresService {
    List<GenresDTO> getAllGenres();
    void addGenres(GenresDTO genresDTO);
    void updateGenres(GenresDTO genresDTO, Long id);
    void deleteGenres(Long genresId);
}
