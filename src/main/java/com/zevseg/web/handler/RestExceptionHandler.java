package com.zevseg.web.handler;

import com.zevseg.web.contant.ExceptionType;
import com.zevseg.web.dto.ErrorResponse;
import com.zevseg.web.exception.*;
import com.zevseg.web.helper.Localization;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sainjargal Ishdorj
 * @created 2022.05.31
 */

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    Localization localization;

    public RestExceptionHandler(Localization localization) {
        this.localization = localization;
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + "|" + x.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setError(null);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setValidations(errors);
        response.setType(ExceptionType.VALIDATION.value());

        return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);

    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {

        return new ResponseEntity<>(new ErrorResponse(status, ExceptionType.VALIDATION.value(), ex.getMessage()), headers, HttpStatus.BAD_REQUEST);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   @NonNull HttpHeaders headers,
                                                                   @NonNull HttpStatus status,
                                                                   @NonNull WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(status, ExceptionType.RUN_TIME.value(), ex.getMessage()), headers, status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMissingPathVariable(
            MissingPathVariableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {

        return new ResponseEntity<>(new ErrorResponse(status, ExceptionType.VALIDATION.value(), ex.getMessage()), headers, status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {

        List<String> errors = Collections.singletonList(ex.getParameterName() + "| " + localization.getMessage("val.not.null"));

        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setError(null);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setValidations(errors);
        response.setType(ExceptionType.VALIDATION.value());

        return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);

    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(status, ExceptionType.RUN_TIME.value(), "Data parse error :("), headers, status);
    }

    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST, ExceptionType.BUSINESS.value(), ex.getMessage(), ex.errorDetails));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleBusinessException(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST, ExceptionType.BUSINESS.value(), localization.getMessage("type.wrong")));
    }

//
//    @ExceptionHandler(value = {ValidationException.class})
//    protected ResponseEntity<Object>  handleValidationException(ValidationException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST, ExceptionType.BUSINESS.value(), ex.getMessage(), ex.errorDetails));
//    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN, ExceptionType.ACCESS_DENIED.value(), localization.getMessage("error.forbidden")));
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    protected ResponseEntity<Object> handleUnauthorized(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(HttpStatus.UNAUTHORIZED, ExceptionType.UNAUTHORIZED.value(), localization.getMessage("error.unauthorized")));
    }

    @ExceptionHandler(value = {RunTimeException.class})
    protected ResponseEntity<Object> handleRunTimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionType.RUN_TIME.value(), localization.getMessage("error.fatal")));
    }

    @ExceptionHandler(value = {RMIException.class})
    protected ResponseEntity<Object> handleRMIException(RMIException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionType.RMI.value(), localization.getMessage("error.fatal")));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionType.FATAL.value(), localization.getMessage("error.fatal")));
    }

    @ExceptionHandler(value = {TokenException.class})
    protected ResponseEntity<Object> handleTokenException(TokenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN, ExceptionType.ACCESS_DENIED.value(), localization.getMessage("error.forbidden")));
    }

}
