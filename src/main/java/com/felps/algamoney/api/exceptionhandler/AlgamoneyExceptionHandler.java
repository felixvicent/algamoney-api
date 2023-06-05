package com.felps.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private MessageSource messageSource;

  @Override
  @Nullable
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {

    String userMessage = messageSource.getMessage("message.invalid", null, LocaleContextHolder.getLocale());
    String devMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();

    List<Error> errors = Arrays.asList(new Error(userMessage, devMessage));

    return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  @Nullable
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {

    List<Error> errors = createErrorList(ex.getBindingResult());
    return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({ DataIntegrityViolationException.class })
  public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
      WebRequest request) {
    String userMessage = messageSource.getMessage("resource.bad-request", null, LocaleContextHolder.getLocale());
    String devMessage = ExceptionUtils.getRootCauseMessage(ex);

    List<Error> errors = Arrays.asList(new Error(userMessage, devMessage));

    return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  private List<Error> createErrorList(BindingResult bindingResult) {
    List<Error> errors = new ArrayList<>();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
      String devMessage = fieldError.toString();

      errors.add(new Error(userMessage, devMessage));
    }

    return errors;
  }

  @ExceptionHandler({ EmptyResultDataAccessException.class })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
      WebRequest request) {
    String userMessage = messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale());
    String devMessage = ex.toString();

    List<Error> errors = Arrays.asList(new Error(userMessage, devMessage));

    return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  public static class Error {

    private String userMessage;
    private String devMessage;

    public Error(String userMessage, String devMessage) {
      this.userMessage = userMessage;
      this.devMessage = devMessage;
    }

    public String getUserMessage() {
      return userMessage;
    }

    public String getDevMessage() {
      return devMessage;
    }
  }
}
