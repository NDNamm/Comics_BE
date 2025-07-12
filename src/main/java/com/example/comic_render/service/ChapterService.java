package com.example.comic_render.service;

import com.example.comic_render.DTO.ChaptersDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ChapterService {

    Page<ChaptersDTO> getChapters(int page, int size);
    ChaptersDTO getChapterBySlug(String comicSlug, String slug);
    void addChapter(String slug,ChaptersDTO chaptersDTO,  MultipartFile[] files);
    void updateChapter(ChaptersDTO chaptersDTO, Long id, MultipartFile[] files);
    void deleteChapter(Long id);
}
