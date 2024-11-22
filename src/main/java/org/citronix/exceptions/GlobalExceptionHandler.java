package org.citronix.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.citronix.exceptions.custom.EntityNotFound;
import org.citronix.utils.response.ApiResponse;
import org.citronix.utils.response.ResponseUtil;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handleGeneralException(Exception ex, HttpServletRequest request) {
        return ResponseUtil.error(List.of(ex.getMessage()), "An unexpected error occurred", 1001, request.getRequestURI());
    }

    @ExceptionHandler(EntityNotFound.class)
    public ApiResponse<Object> handleNotFoundException(Exception ex, HttpServletRequest request) {
        return ResponseUtil.error(List.of(ex.getMessage()), "Entity not found", 1001, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return ResponseUtil.error(
                errors,
                "Validation failed",
                1002,
                request.getRequestURI()
        );
    }
}