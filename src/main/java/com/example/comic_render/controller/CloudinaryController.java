package com.example.comic_render.controller;

import com.example.comic_render.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class CloudinaryController {

    @Autowired
    private ImagesService imagesService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam("folder") String folder) {
        try {
            imagesService.uploadComics(file,folder);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Loi" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

