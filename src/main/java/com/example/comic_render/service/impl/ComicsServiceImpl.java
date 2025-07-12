package com.example.comic_render.service.impl;

import com.example.comic_render.DTO.ChaptersDTO;
import com.example.comic_render.DTO.ComicsDTO;
import com.example.comic_render.Entity.*;
import com.example.comic_render.Repository.ChapterRepository;
import com.example.comic_render.Repository.ComicRepository;
import com.example.comic_render.Repository.GenreRepository;
import com.example.comic_render.exception.AppException;
import com.example.comic_render.exception.ErrorCode;
import com.example.comic_render.service.ImagesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.comic_render.service.ComicsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ComicsServiceImpl implements ComicsService {

    private final ComicRepository comicRepository;
    private final ChapterRepository chapterRepository;
    private final ImagesService imagesService;
    private final GenreRepository genreRepository;

    @Override
    public Page<ComicsDTO> getAllComics(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Comics> comics = comicRepository.findAll(pageable);

        return comics.map(this::convertToDTO);
    }

    private ComicsDTO convertToDTO(Comics comic) {
        List<ChaptersDTO> chaptersDTOS = null;

        List<Chapters> chapters = comic.getChapters();
        if (chapters != null && !chapters.isEmpty()) {
            chaptersDTOS = chapters.stream()
                    .map(chap -> ChaptersDTO.builder()
                            .id(chap.getId())
                            .name(chap.getName())
                            .slug(chap.getSlug())
                            .createdAt(chap.getCreatedAt())
                            .build())
                    .toList();
        }

        List<String> genre = comic.getGenres().stream()
                .map(Genres::getGenreName)
                .toList();

        return ComicsDTO.builder()
                .id(comic.getId())
                .name(comic.getName())
                .slug(comic.getSlug())
                .status(comic.getStatus())
                .description(comic.getDescription())
                .coverUrl(comic.getCoverUrl())
                .createdAt(comic.getCreatedAt())
                .author(comic.getAuthor())
                .genreNames(genre)
                .chapters(chaptersDTOS)
                .build();
    }

    //Chi tiet truyen
    @Override
    public ComicsDTO getComicBySlug(String slug) {
        Comics comics = comicRepository.findBySlug(slug).
                orElseThrow(() -> new AppException(ErrorCode.COMIC_NOT_FOUND));

        return convertToDTO(comics);
    }

    @Override
    public void addComics(ComicsDTO comicsDTO, MultipartFile file) {

        if (comicRepository.findBySlug(comicsDTO.getSlug()).isPresent()) {
            throw new AppException(ErrorCode.COMIC_NAME_EXISTED);
        }

        try {
            String imageUrl = imagesService.uploadComics(file, toSlug(comicsDTO.getName()));

            Set<Genres> genreSet = new HashSet<>();
            for (Long id : comicsDTO.getGenreId()) {
                Genres genres = genreRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.GENRES_NOT_FOUND));
                genreSet.add(genres);
            }

            Comics comics = Comics.builder()
                    .name(comicsDTO.getName())
                    .slug(toSlug(comicsDTO.getName()))
                    .description(comicsDTO.getDescription())
                    .coverUrl(imageUrl)
                    .genres(genreSet)
                    .createdAt(LocalDateTime.now())
                    .status(EnumStatus.ONGOING)
                    .author(comicsDTO.getAuthor())
                    .build();

            comicRepository.save(comics);

        } catch (IOException e) {
            throw new AppException(ErrorCode.UPDATE_IMAGE_FAIL);
        }
    }


    public String toSlug(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("^-|-$", "");
    }


    @Override
    public void updateComics(ComicsDTO comicsDTO, Long id, MultipartFile file) {

        Comics comics = comicRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMIC_NOT_FOUND));

        try {
            if (file != null && !file.isEmpty()) {
                String url = imagesService.uploadComics(file, toSlug(comicsDTO.getSlug()));
                comics.setCoverUrl(url);
            }

            if (comicsDTO.getAuthor() != null) {
                comics.setAuthor(comicsDTO.getAuthor());
            }

            if (comicsDTO.getGenreId() != null && !comicsDTO.getGenreId().isEmpty()) {
                Set<Genres> genreSet = new HashSet<>();
                for (Long genreId : comicsDTO.getGenreId()) {
                    Genres genre = genreRepository.findById(genreId)
                            .orElseThrow(() -> new AppException(ErrorCode.GENRES_NOT_FOUND));
                    genreSet.add(genre);
                }
                comics.setGenres(genreSet);
            }
            comics.setStatus(EnumStatus.ONGOING);
            comics.setName(comicsDTO.getName());
            comics.setDescription(comicsDTO.getDescription());
            comicRepository.save(comics);
        } catch (IOException e) {
            throw new AppException(ErrorCode.UPDATE_IMAGE_FAIL);
        }

    }

    @Override
    public void deleteComics(Long id) {
        Comics comics = comicRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMIC_NOT_FOUND));

        chapterRepository.deleteChaptersByComicId(id);

        comics.getGenres().clear();
        comicRepository.save(comics);
        comicRepository.delete(comics);
    }

    @Override
    public Page<ComicsDTO> searchAllComics(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Comics> comics = comicRepository.findByNameOrSlug(keyword, pageable);

        return comics.map(this::convertToDTO);
    }

    @Override
    public Page<ComicsDTO> searchComicsByGenres(List<Long> genreId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Comics> comics = comicRepository.findComicsByGenres(genreId, (long) genreId.size(), pageable);

        return comics.map(this::convertToDTO);
    }


}
