package kyonggiyo.global.advice;

import kyonggiyo.domain.auth.exception.ExpiredTokenException;
import kyonggiyo.global.exception.AuthenticationException;
import kyonggiyo.global.exception.ForbiddenException;
import kyonggiyo.global.exception.GlobalErrorCode;
import kyonggiyo.global.exception.InvalidStateException;
import kyonggiyo.global.exception.InvalidValueException;
import kyonggiyo.global.exception.NotFoundException;
import kyonggiyo.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(Exception e) {
        log.warn("예상치 못한 서버 예외가 발생하였습니다.", e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(GlobalErrorCode.INTERNAL_SERVER_EXCEPTION));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(ExpiredTokenException exception) {
        log.warn(exception.getLoggingMessage(), exception);
        return ResponseEntity.status(BAD_REQUEST)
                .body(ErrorResponse.of(exception.getErrorCode()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(InvalidValueException exception) {
        log.warn(exception.getLoggingMessage(), exception);
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(exception.getErrorCode()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(InvalidStateException exception) {
        log.warn(exception.getLoggingMessage(), exception);
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(exception.getErrorCode()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(AuthenticationException exception) {
        log.warn(exception.getLoggingMessage(), exception);
        return ResponseEntity.status(UNAUTHORIZED)
                .body(ErrorResponse.of(exception.getErrorCode()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(ForbiddenException exception) {
        log.warn(exception.getLoggingMessage(), exception);
        return ResponseEntity.status(FORBIDDEN)
                .body(ErrorResponse.of(exception.getErrorCode()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(NotFoundException exception) {
        log.warn(exception.getLoggingMessage(), exception);
        return ResponseEntity.status(NOT_FOUND)
                .body(ErrorResponse.of(exception.getErrorCode()));
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(MethodArgumentNotValidException exception) {
        log.warn(exception.getMessage(), exception.getBindingResult());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(GlobalErrorCode.INVALID_REQUEST_EXCEPTION, exception.getBindingResult()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(MethodArgumentTypeMismatchException exception) {
        log.warn(exception.getMessage(), exception);
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(exception));
    }

}
