package com.athletic.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException exception) {
        int status = exception.getErrorCode().getStatus();
        String name = exception.getErrorCode().name();
        String message = exception.getErrorCode().getMessage();
        Object parameter = exception.getParameter();
        message = convertMessage(message, parameter);
        if (log.isErrorEnabled())
            log.error("CustomException : {} ({})", name, message);
        return ErrorResponseEntity.toResponseEntity(status, name, message);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseEntity> handleException(Exception e) {
        if (log.isErrorEnabled())
            log.error("Unknown Exception : {}", e.getMessage());
        return handleCustomException(new CustomException(ErrorCode.UNKNOWN));
    }

    @SuppressWarnings("unchecked")
    private String convertMessage(String message, Object parameter) {
        String convertMessage = message;
        if (parameter instanceof String) {
            convertMessage = convertMessage.replace("{0}", parameter.toString());
        } else if (parameter instanceof List) {
            int i = 0;
            for (String param : (List<String>) parameter) {
                String target = "{" + i++ + "}";
                convertMessage = convertMessage.replace(target, param);
            }
        } else if (parameter instanceof String[]) {
            int i = 0;
            for (String param : (String[]) parameter) {
                String target = "{" + i++ + "}";
                convertMessage = convertMessage.replace(target, param);
            }
        }
        return convertMessage;
    }
}
