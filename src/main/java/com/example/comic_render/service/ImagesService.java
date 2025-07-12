package com.example.comic_render.service;

import com.example.comic_render.Entity.Chapters;
import com.example.comic_render.Entity.Images;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImagesService {
    String uploadComics(MultipartFile file, String slug) throws IOException;

    List<Images> uploadChapter(MultipartFile[] file, String name, Chapters chapters) throws IOException;

}
