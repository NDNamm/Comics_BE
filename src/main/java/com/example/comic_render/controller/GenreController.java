package com.example.comic_render.controller;

import com.example.comic_render.DTO.ApiResponse;
import com.example.comic_render.DTO.GenresDTO;
import com.example.comic_render.service.GenresService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
@RequiredArgsConstructor
public class GenreController {

    private final GenresService genresService;

    @GetMapping("")
    public ApiResponse<List<GenresDTO>> getAllGenres() {

        ApiResponse<List<GenresDTO>> response = new ApiResponse<>();
        response.setData(genresService.getAllGenres());
        return response;
    }

    @PostMapping("/add")
    public ApiResponse<GenresDTO> addGenres(@RequestBody GenresDTO genresDTO) {

        ApiResponse<GenresDTO> response = new ApiResponse<>();
        genresService.addGenres(genresDTO);
        response.setMessage("Thêm thể loại thành công");
        return response;
    }


    @PostMapping("update/{id}")
    public ApiResponse<GenresDTO> updateGenres(@PathVariable Long id,
                                               @RequestBody GenresDTO genresDTO) {
        ApiResponse<GenresDTO> response = new ApiResponse<>();
        genresService.updateGenres(genresDTO, id);
        response.setMessage("Sửa thể loại thành công");
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<GenresDTO> deleteGenres(@PathVariable Long id) {

        ApiResponse<GenresDTO> response = new ApiResponse<>();
        genresService.deleteGenres(id);
        response.setMessage("Xóa thể loại thành công");
        return response;
    }

}
