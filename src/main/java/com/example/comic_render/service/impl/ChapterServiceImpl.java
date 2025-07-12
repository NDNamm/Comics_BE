package com.example.comic_render.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.comic_render.DTO.ChaptersDTO;
import com.example.comic_render.DTO.ImagesDTO;
import com.example.comic_render.Entity.Chapters;
import com.example.comic_render.Entity.Comics;
import com.example.comic_render.Entity.Images;
import com.example.comic_render.Repository.ChapterRepository;
import com.example.comic_render.Repository.ComicRepository;
import com.example.comic_render.Repository.ImagesRepository;
import com.example.comic_render.exception.AppException;
import com.example.comic_render.exception.ErrorCode;
import com.example.comic_render.service.ChapterService;
import com.example.comic_render.service.ImagesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final ComicRepository comicRepository;
    private final ImagesRepository imagesRepository;
    private final Cloudinary cloudinary;
    private final ImagesService imagesService;

    @Override
    public Page<ChaptersDTO> getChapters(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Chapters> chapters = chapterRepository.findAll(pageable);

        return chapters.map(this::getChapterDTO);
    }

    private ChaptersDTO getChapterDTO(Chapters chapters) {

        return ChaptersDTO.builder()
                .id(chapters.getId())
                .name(chapters.getName())
                .slug(chapters.getSlug())
                .content(chapters.getContent())
                .createdAt(chapters.getCreatedAt())
                .build();
    }

    @Override
    public ChaptersDTO getChapterBySlug(String comicSlug, String slug) {
        Comics comics = comicRepository.findBySlug(comicSlug).
                orElseThrow(() -> new AppException(ErrorCode.COMIC_NOT_FOUND));

        Chapters chapters = chapterRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));

        if (!chapters.getComic().getId().equals(comics.getId())) {
            throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
        }

        List<ImagesDTO> imagesDTOS = chapters.getImages().stream()
                .map(images -> ImagesDTO.builder()
                        .id(images.getId())
                        .publicId(images.getPublicId())
                        .imageUrl(images.getImageUrl())
                        .build())
                .toList();

        return ChaptersDTO.builder()
                .id(chapters.getId())
                .slug(chapters.getSlug())
                .content(chapters.getContent())
                .createdAt(chapters.getCreatedAt())
                .name(chapters.getName())
                .imagesUrl(imagesDTOS)
                .build();
    }

    @Override
    @Transactional
    public void addChapter(String comicSlug, ChaptersDTO chaptersDTO, MultipartFile[] files) {

        Comics comics = comicRepository.findBySlug(comicSlug)
                .orElseThrow(() -> new AppException(ErrorCode.COMIC_NOT_FOUND));

        Chapters chapters = chapterRepository.findBySlug(chaptersDTO.getSlug())
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));

        if (!chapters.getComic().getId().equals(comics.getId())) {
            throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
        }

        try {
            Chapters chapter = Chapters.builder()
                    .name(chaptersDTO.getName())
                    .slug(chaptersDTO.getSlug())
                    .content(chaptersDTO.getContent())
                    .createdAt(LocalDateTime.now())
                    .build();

            List<Images> images = imagesService.uploadChapter(files, comicSlug, chapter);
            chapter.setImages(images);
            chapterRepository.save(chapter);

        } catch (IOException e) {
            throw new AppException(ErrorCode.UPDATE_IMAGE_FAIL);
        }
    }

    @Override
    @Transactional
    public void updateChapter(ChaptersDTO chaptersDTO, Long id, MultipartFile[] files) {

        Chapters chapters = chapterRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));

        try {
            if (chaptersDTO.getImagesUrl() != null && !chaptersDTO.getImagesUrl().isEmpty()) {
                List<Images> oldImages = chapters.getImages();
                if (!oldImages.isEmpty()) {
                    for (Images image : oldImages) {
                        //Xoa anh cu
                        cloudinary.uploader().destroy(image.getPublicId(), ObjectUtils.emptyMap());
                        //Xoa o DB
                        imagesRepository.delete(image);
                    }
                }
                List<Images> images = imagesService.uploadChapter(files, chaptersDTO.getSlug(), chapters);
                chapters.setImages(images);
            }
            chapters.setName(chaptersDTO.getName());
            chapters.setSlug(chaptersDTO.getSlug());
            chapters.setContent(chaptersDTO.getContent());
            chapters.setCreatedAt(LocalDateTime.now());

            chapterRepository.save(chapters);
        } catch (IOException e) {
            throw new AppException(ErrorCode.UPDATE_IMAGE_FAIL);
        }

    }

    @Override
    public void deleteChapter(Long id) {

        Chapters chapters = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chap k ton tai"));

        List<Images> oldImages = chapters.getImages();
        try {
            if (!oldImages.isEmpty()) {
                for (Images image : oldImages) {
                    //Xoa anh cu
                    cloudinary.uploader().destroy(image.getPublicId(), ObjectUtils.emptyMap());
                    //Xoa o DB
                    imagesRepository.delete(image);
                }
            }
            chapterRepository.delete(chapters);
        } catch (IOException e) {
            throw new AppException(ErrorCode.DELETE_IMAGE_FAIL);
        }

    }
}
