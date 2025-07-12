package com.example.comic_render.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.comic_render.Entity.Chapters;
import com.example.comic_render.Entity.Images;
import com.example.comic_render.Repository.ImagesRepository;
import com.example.comic_render.exception.AppException;
import com.example.comic_render.exception.ErrorCode;
import com.example.comic_render.service.ImagesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ImagesServiceImpl implements ImagesService {

    private final Cloudinary cloudinary;
    private final ImagesRepository imagesRepository;

    @Override
    @Transactional
    public String uploadComics(MultipartFile file, String slug) {

        try {
            Map<?,?> uploadComic = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", "comics-covers"
            ));

            return uploadComic.get("secure_url").toString();
        } catch (IOException e) {
            throw new AppException(ErrorCode.UPDATE_IMAGE_FAIL);
        }

    }

    @Override
    @Transactional
    public List<Images> uploadChapter(MultipartFile[] file, String slug, Chapters chapters){

        List<Images> list = new ArrayList<>();
        for (MultipartFile fileItem : file) {
            try {
                Map<?, ?> results = cloudinary.uploader().upload(fileItem.getBytes(), ObjectUtils.asMap(
                        "folder",slug
                ));

                String url = results.get("secure_url").toString();
                Long size = (Long) results.get("size");
                String publicId = results.get("public_id").toString();

                Images images = new Images();
                images.setImageUrl(url);
                images.setPublicId(publicId);
                images.setSize(size);
                images.setChapter(chapters);
                imagesRepository.save(images);

                list.add(images);
            } catch (IOException e) {
                throw new AppException(ErrorCode.UPDATE_IMAGE_FAIL);
            }
        }
        return list;
    }
}
