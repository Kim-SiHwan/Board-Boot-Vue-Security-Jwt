package com.example.boardbvsj.exception;

import com.example.boardbvsj.dto.error.ErrorResponseDto;
import com.example.boardbvsj.exception.customException.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> userNotFoundException(UserNotFoundException e){
        return new ResponseEntity<>(errorResponseDto(ErrorCode.INVALID_LOGIN_INFO.getCode(),ErrorCode.INVALID_LOGIN_INFO.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponseDto> badCredentialsException(BadCredentialsException e){
        return new ResponseEntity<>(errorResponseDto(ErrorCode.INVALID_LOGIN_INFO.getCode(),ErrorCode.INVALID_LOGIN_INFO.getDescription()),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UsernameDuplicatedException.class)
    protected ResponseEntity<ErrorResponseDto> usernameDuplicatedException(UsernameDuplicatedException e){
        return new ResponseEntity<>(errorResponseDto(ErrorCode.DUPLICATED_USERNAME.getCode(),ErrorCode.DUPLICATED_USERNAME.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BoardNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> boardNotFoundException(BoardNotFoundException e){
        return new ResponseEntity<>(errorResponseDto(ErrorCode.DELETED_BOARD.getCode(),ErrorCode.DELETED_BOARD.getDescription()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ReplyNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> replyNotFoundException(ReplyNotFoundException e){
        return new ResponseEntity<>(errorResponseDto(ErrorCode.DELETED_REPLY.getCode(),ErrorCode.DELETED_REPLY.getDescription()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException e){
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return new ResponseEntity<>(errorResponseDto(ErrorCode.NOT_NULL.getCode(), message),HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(DifferentUsernameException.class)
    protected ResponseEntity<ErrorResponseDto> differentUsernameException(DifferentUsernameException e){
        return new ResponseEntity<>(errorResponseDto(ErrorCode.FORBIDDEN_ACCESS.getCode(),ErrorCode.FORBIDDEN_ACCESS.getDescription()),HttpStatus.BAD_REQUEST);
    }

    private ErrorResponseDto errorResponseDto(int code, String message){
        return new ErrorResponseDto(false,code,message);

    }

}
