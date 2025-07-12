package com.example.comic_render.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // COMIC
    COMIC_NOT_FOUND(2001, "Comic not found", HttpStatus.NOT_FOUND),
    COMIC_NAME_EXISTED(2002, "Comic name already exists", HttpStatus.BAD_REQUEST),
    COMIC_STATUS_INVALID(2004, "Comic product status", HttpStatus.BAD_REQUEST),
    COMIC_NAME_INVALID(2005, "Comic name is invalid", HttpStatus.BAD_REQUEST),
    COMIC_OUT_OF_STOCK(2006,"The Comic is out of stock or discontinued", HttpStatus.BAD_REQUEST),

    //GENRES
    GENRES_NOT_FOUND(3001, "Genres not found", HttpStatus.NOT_FOUND),
    GENRES_NAME_EXISTED(3002, "Genres name already exists", HttpStatus.BAD_REQUEST),
    GENRES_STATUS_INVALID(3004, "Invalid Genres status", HttpStatus.BAD_REQUEST),
    GENRES_NAME_INVALID(3005, "Genres name is invalid", HttpStatus.BAD_REQUEST),
    GENRES_OUT_OF_STOCK(3006,"The Genres is out of stock or discontinued", HttpStatus.BAD_REQUEST),

    //Images
    UPDATE_IMAGE_FAIL(4001, "Error update image", HttpStatus.BAD_REQUEST),
    DELETE_IMAGE_FAIL(4002, "Error delete image", HttpStatus.BAD_REQUEST),

    // CHAPTER
    CHAPTER_NOT_FOUND(5001, "Chapter not found", HttpStatus.NOT_FOUND),
    CHAPTER_NAME_EXISTED(5002, "Chapter name already exists", HttpStatus.BAD_REQUEST),
    CHAPTER_STATUS_INVALID(5004, "Chapter status", HttpStatus.BAD_REQUEST),
    CHAPTER_NAME_INVALID(5005, "Chapter name is invalid", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    private final int code;
    private final String message;
    private final HttpStatus status;


}
