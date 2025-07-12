//package com.example.comic_render.controller;
//
//import com.example.comic_render.DTO.ChaptersDTO;
//import com.example.comic_render.service.ChapterService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/api/chapters")
//public class ChapterController {
//
//    @Autowired
//    private ChapterService chapterService;
//
//    @GetMapping("")
//    ResponseEntity<?> getAllChapters(@RequestParam(defaultValue = "0") int page,
//                                     @RequestParam(defaultValue = "10") int size) {
//        try {
//            Page<ChaptersDTO> chaptersDTOS = chapterService.getChapters(page, size);
//            return ResponseEntity.ok().body(chaptersDTOS);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loi :" + e.getMessage());
//        }
//    }
//
//    @PostMapping("/add/{comicSlug}")
//    ResponseEntity<?> addChapter(@PathVariable String comicSlug,
//                                 @RequestPart("chapterDTO") String chaptersJson,
//                                 @RequestPart("file") MultipartFile[] file) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            ChaptersDTO chaptersDTO = mapper.readValue(chaptersJson, ChaptersDTO.class);
//            chapterService.addChapter(comicSlug,chaptersDTO,file);
//            return ResponseEntity.status(HttpStatus.CREATED).body("Them chapter thanh cong cho truyen: " + comicSlug );
//        }
//        catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Loi :" + e.getMessage());
//        }
//    }
//
////    @GetMapping("/{comicId}")
////    ResponseEntity<?> getAllChaptersByComicId(@PathVariable long comicId, @RequestParam(defaultValue = "0") int page,
////                                              @RequestParam(defaultValue = "10") int size) {
////        try {
////            Page<ChaptersDTO> chaptersDTOS = chapterService.getChaptersByIdComics(comicId, page, size);
////            return ResponseEntity.ok().body(chaptersDTOS);
////        }
////        catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loi :" + e.getMessage());
////        }
////    }
//
//
//    @PutMapping("/update/{id}")
//    ResponseEntity<?> updateChapter(@RequestPart("chapterDTO") String chaptersJson,
//                                    @RequestPart("file") MultipartFile[] files,
//                                    @PathVariable Long id) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            ChaptersDTO chaptersDTO = mapper.readValue(chaptersJson, ChaptersDTO.class);
//            chapterService.updateChapter(chaptersDTO,id,files);
//            return ResponseEntity.status(HttpStatus.UPGRADE_REQUIRED).body("Update chap thanh cong");
//        }
//        catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Loi :" + e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/delete/{id}")
//    ResponseEntity<?> deleteChapter(@PathVariable Long id) {
//        try {
//            chapterService.deleteChapter(id);
//            return ResponseEntity.status(HttpStatus.OK).body("Delete chap thanh cong");
//        }
//        catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Loi :" + e.getMessage());
//        }
//    }
//}
