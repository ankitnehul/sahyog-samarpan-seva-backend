package com.sahyogsamarpan.seva.event;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahyogsamarpan.seva.event.dto.EventRequest;
import com.sahyogsamarpan.seva.event.dto.EventResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /** Public — used by the website later. */
    @GetMapping
    public List<EventResponse> listEvents() {
        return eventService.listAll();
    }

    /** Public — single event by slug (e.g. maharashtra-day-2026). */
    @GetMapping("/{slug}")
    public EventResponse getEvent(@PathVariable String slug) {
        return eventService.getBySlug(slug);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest request) {
        EventResponse created = eventService.create(request);
        return ResponseEntity.created(URI.create("/api/events/" + created.id())).body(created);
    }

    @PutMapping("/{slug}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public EventResponse updateEvent(@PathVariable String slug, @Valid @RequestBody EventRequest request) {
        return eventService.update(slug, request);
    }

    @DeleteMapping("/{slug}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEvent(@PathVariable String slug) {
        eventService.delete(slug);
        return ResponseEntity.noContent().build();
    }
}
