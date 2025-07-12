package com.example.comic_render.service.impl;

import com.example.comic_render.DTO.GenresDTO;
import com.example.comic_render.Entity.Genres;
import com.example.comic_render.Repository.GenreRepository;
import com.example.comic_render.exception.AppException;
import com.example.comic_render.exception.ErrorCode;
import com.example.comic_render.service.GenresService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenresServiceImpl implements GenresService {

    private final GenreRepository genreRepository;

    @Override
    public List<GenresDTO> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(genre -> {
                    GenresDTO genresDTO = new GenresDTO();
                    genresDTO.setId(genre.getId());
                    genresDTO.setGenreName(genre.getGenreName());
                    return genresDTO;
                })
                .toList();
    }

    @Override
    public void addGenres(GenresDTO genresDTO) {
        List<Genres> genres = genreRepository.findByName(genresDTO.getGenreName());

        if (genres.isEmpty()){
            Genres genres1 = new Genres();
            genres1.setGenreName(genresDTO.getGenreName());
            genreRepository.save(genres1);
        }
        else {
            throw new AppException(ErrorCode.GENRES_NAME_EXISTED);
        }

    }

    @Override
    public void updateGenres(GenresDTO genresDTO, Long id) {

        Genres genres = genreRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.GENRES_NOT_FOUND));

        genres.setGenreName(genresDTO.getGenreName());
        genreRepository.save(genres);
    }

    @Override
    public void deleteGenres(Long genresId) {
        Genres genres = genreRepository.findById(genresId)
                .orElseThrow(() -> new AppException(ErrorCode.GENRES_NOT_FOUND));
        genreRepository.delete(genres);
    }

}
