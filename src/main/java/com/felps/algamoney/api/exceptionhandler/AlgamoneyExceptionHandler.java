package com.felps.algamoney.api.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
    String devMessage = ex.getCause().toString();

    return handleExceptionInternal(ex, new Error(userMessage, devMessage), headers, HttpStatus.BAD_REQUEST, request);
  }

  public static class Error{
    
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