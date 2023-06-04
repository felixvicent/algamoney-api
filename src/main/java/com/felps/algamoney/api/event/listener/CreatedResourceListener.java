package com.felps.algamoney.api.event.listener;

import java.net.URI;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.felps.algamoney.api.event.CreatedResourceEvent;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class CreatedResourceListener implements ApplicationListener<CreatedResourceEvent> {

  @Override
  public void onApplicationEvent(CreatedResourceEvent event) {
    HttpServletResponse response = event.getResponse();
    Long id = event.getId();

    addHeaderLocation(response, id);
  } 

  public void addHeaderLocation(HttpServletResponse response, Long id) {
    URI uri = ServletUriComponentsBuilder
      .fromCurrentRequestUri()
      .path("/{id}")
      .buildAndExpand(id)
      .toUri();

    response.setHeader("Location", uri.toASCIIString());
  }
}
