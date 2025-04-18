package org.duckdns.celebritystrike.celebritystrike.exception;

import lombok.extern.slf4j.Slf4j;
import org.duckdns.celebritystrike.celebritystrike.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String GAME_NOT_FOUND = "Game not found: %s";
    private static final String UNHANDLED_EXCEPTION = "Unhandled exception: {}";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        response.put("errors", errors);
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("timestamp", LocalDateTime.now());

        log.error("Validation error: {}", errors);
        return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleGeneralException(Exception ex) {
        log.error(UNHANDLED_EXCEPTION, ex.getMessage());
        return  Result.<String>builder().success(false).message(ex.getMessage()).build();
    }

    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<String> handleHttpMessageConversionException(HttpMessageConversionException ex) {
        log.info("Game not found: {}", ex.getMessage());
        return Result.<String>builder().success(false).message(String.format(GAME_NOT_FOUND, ex.getMessage())).build();
    }

    @ExceptionHandler(TelegramMessageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleTelegramMessageException(TelegramMessageException ex) {
        log.error("Telegram message error: {}", ex.getMessage());
        return Result.<String>builder()
                .success(false)
                .message("Telegram message error: " + ex.getMessage())
                .build();
    }
}

