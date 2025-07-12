package com.example.comic_render.exception;

import com.example.comic_render.DTO.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<Map<String, String>> handleRuntimeException(Exception e, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(map);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException e, HttpServletRequest request) {
        ErrorCode errorCode = e.getErrorCode();

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
