package com.hanssem.remodeling.content.common.advice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.common.response.Response;
import com.hanssem.remodeling.content.common.util.Utility;
import com.hanssem.remodeling.content.constant.ResponseCode;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintDeclarationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response> customException(CustomException e) {
        log.error("CustomException -> {}", e.getMessage());
        if (Objects.isNull(e.getData())) {
            return response(e.getCode(), e.getMessage());
        }
        return response(e.getCode(), e.getMessage(), e.getData());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> BadRequestException(Exception e) {
        log.error("BadRequestException -> {}", e.getMessage());
        return response(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<Response> FileUploadException(FileUploadException e) {
        log.error("FileUploadException -> {}", e.getMessage());
        return response(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Response> invalidParams(InvalidParameterException e) {
        log.error("InvalidParameterException -> {}", e.getMessage());
        return response(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, JsonParseException.class, JsonMappingException.class})
    public ResponseEntity<Response> httpMessageNotReadable(HttpServletRequest req, HttpMessageNotReadableException e) {

        String msg = "";
        Throwable throwable = e.getMostSpecificCause();

        if (throwable instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) throwable;
            if (ife.getTargetType().isEnum()) {
                msg = ((InvalidFormatException) throwable).getPath().get(0).getFieldName() + " is possible " + Arrays.toString(ife.getTargetType().getEnumConstants());
            } else {
                msg = "Please check the error type of reverse serialization.";
            }
        } else {
            msg = "Please check your json grammar.";
        }
        log.error("exception -> {}", msg);
        return response(HttpStatus.BAD_REQUEST, msg);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> SqlException(Exception e) {
        log.error("exception -> {}", e.getMessage());
        e.printStackTrace();
        return response(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<Response> RequestNotValidHandler(Exception e) {

        List<FieldError> errors = new ArrayList<>();

        if (e instanceof MethodArgumentNotValidException) {
            final MethodArgumentNotValidException mane = (MethodArgumentNotValidException) e;
            final BindingResult bindingResult = mane.getBindingResult();
            errors = bindingResult.getFieldErrors();
        } else if (e instanceof BindException) {
            final BindException be = (BindException) e;
            errors = be.getFieldErrors();
        }

        final StringJoiner sj = Utility.stringWithBlank();
        errors.forEach(
                error ->
//                        sj.add(error.getField()).add(Objects.requireNonNull(error.getRejectedValue()).toString()).add(error.getDefaultMessage())
                        sj.add(error.getDefaultMessage())
        );

        log.error("RequestNotValidException -> " + e.getMessage());
        return response(HttpStatus.BAD_REQUEST, sj.toString());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String msg = "";
        if (e.getRequiredType().getEnumConstants() != null) {
            msg = Arrays.toString(e.getRequiredType().getEnumConstants());
            final StringJoiner sj = Utility.stringWithBlank();
//            sj.add(ErrorCode.DEFINED_VALUE.getMessage()).add(msg);
            msg = sj.toString();
        } else {
//            msg = ErrorCode.INVALID_PARAMETER.getMessage();
        }
        log.error("MethodArgumentTypeMismatchException -> " + msg);
        return response(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Response> HttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("HttpMediaTypeNotSupportedException -> " + e.getMessage());
        return response(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Response> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException -> {}", e.getMessage());
        return response(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(ConstraintDeclarationException.class)
    public ResponseEntity<Response> ConstraintDeclarationException(ConstraintDeclarationException cde) {
        log.error("ConstraintDeclarationException");
        return response(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Response> EmptyResultDataAccessException() {
        log.error("EmptyResultDataAccessException");
        return response(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidPropertyException.class)
    public ResponseEntity<Response> InvalidPropertyException(InvalidPropertyException ipe) {
        log.error("InvalidPropertyException -> " + ipe.getMessage());
        return response(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Response> NoHandlerFoundException() {
        log.error("NoHandlerFoundException");
        return response(HttpStatus.NOT_FOUND, ResponseCode.NOT_FOUND.getMessage());
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Response> MissingServletRequestPartException(MissingServletRequestPartException exception) {
        log.error("MissingServletRequestPartException -> " + exception.getMessage());
        return response(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Response> MissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.error("MissingServletRequestParameterException -> " + exception.getMessage());
        return response(HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Response> response(final int status, final String message) {
        return ResponseEntity.ok(new Response<ResponseCode>(status, message, null));
    }

    private ResponseEntity<Response> response(final int status, final String message, final Map<String, String> data) {
        return ResponseEntity.ok(new Response<>(status, message, data));
    }

    private ResponseEntity<Response> response(final HttpStatus status) {
        return response(status.value(), status.getReasonPhrase());
    }

    private ResponseEntity<Response> response(final HttpStatus status, String message) {
        return ResponseEntity.ok(new Response<ResponseCode>(status.value(), message, null));
    }
}
