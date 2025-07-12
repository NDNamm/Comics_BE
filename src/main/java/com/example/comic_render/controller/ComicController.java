package com.example.comic_render.controller;

import com.example.comic_render.DTO.ApiResponse;
import com.example.comic_render.DTO.ChaptersDTO;
import com.example.comic_render.DTO.ComicsDTO;
import com.example.comic_render.service.ChapterService;
import com.example.comic_render.service.ComicsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comics")
public class ComicController {

    private final ComicsService comicsService;
    private final ChapterService chapterService;

    @GetMapping("")
    public ApiResponse<Page<ComicsDTO>> getAllComics(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "6") int size) {

        ApiResponse<Page<ComicsDTO>> response = new ApiResponse<>();
        response.setData(comicsService.getAllComics(page, size));
        return response;
    }

    @GetMapping("/{slug}")
    public ApiResponse<ComicsDTO> getComic(@PathVariable String slug) {

        ApiResponse<ComicsDTO> response = new ApiResponse<>();
        response.setData(comicsService.getComicBySlug(slug));
        return response;
    }

    @GetMapping("/{comicSlug}/chapters/{chapterSlug}")
    public ApiResponse<ChaptersDTO> getComicChap(@PathVariable String comicSlug, @PathVariable String chapterSlug) {
        ApiResponse<ChaptersDTO> response = new ApiResponse<>();
        response.setData(chapterService.getChapterBySlug(comicSlug, chapterSlug));
        return response;
    }


    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ComicsDTO> addComic(@RequestPart("comicsDTO") String comicsJson,
                                           @RequestPart("image") MultipartFile image) {

        ApiResponse<ComicsDTO> response = new ApiResponse<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            ComicsDTO comicsDTO = mapper.readValue(comicsJson, ComicsDTO.class);
            comicsService.addComics(comicsDTO, image);
            response.setMessage("Thêm truyện thành công");
            return response;
        } catch (Exception e) {
            response.setCode(9999);
            response.setMessage("Thêm truyện thất bại: " + e.getMessage());
            return response;
        }

    }

    @PutMapping("/update/{id}")
    public ApiResponse<ComicsDTO> updateComic(@PathVariable Long id,
                                              @RequestPart("comicsDTO") String comicsJson,
                                              @RequestPart("file") MultipartFile file) {
        ApiResponse<ComicsDTO> response = new ApiResponse<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            ComicsDTO comicsDTO = mapper.readValue(comicsJson, ComicsDTO.class);
            comicsService.updateComics(comicsDTO, id, file);
            response.setMessage("Update truyện thành công");
            return response;
        } catch (Exception e) {
            response.setCode(9999);
            response.setMessage("Update truyện thất bại: " + e.getMessage());
            return response;
        }

    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<ComicsDTO> deleteComic(@PathVariable Long id) {

        ApiResponse<ComicsDTO> response = new ApiResponse<>();
        comicsService.deleteComics(id);
        response.setMessage("Delete truyện thành công");
        return response;

    }

    @GetMapping("/search/{keyword}")
    public ApiResponse<Page<ComicsDTO>> searchComic(@PathVariable String keyword,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "6") int size) {
        ApiResponse<Page<ComicsDTO>> response = new ApiResponse<>();
        response.setData(comicsService.searchAllComics(keyword, page, size));
        return response;

    }

    @GetMapping("/search/genres")
    public ApiResponse<Page<ComicsDTO>> searchComicByGenre(@RequestParam List<Long> genreId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "6") int size) {

        ApiResponse<Page<ComicsDTO>> response = new ApiResponse<>();
        response.setData(comicsService.searchComicsByGenres(genreId,page, size));
        return response;
    }

}
