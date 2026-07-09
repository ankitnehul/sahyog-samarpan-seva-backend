package com.sahyogsamarpan.seva.event;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sahyogsamarpan.seva.common.ResourceNotFoundException;
import com.sahyogsamarpan.seva.event.dto.EventRequest;
import com.sahyogsamarpan.seva.event.dto.EventResponse;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional(readOnly = true)
    public List<EventResponse> listAll() {
        return eventRepository.findAllByOrderByEventDateDesc().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EventResponse getBySlug(String slug) {
        return toResponse(findEventOrThrow(slug));
    }

    @Transactional
    public EventResponse create(EventRequest request) {
        if (eventRepository.existsBySlug(request.getSlug())) {
            throw new IllegalArgumentException("Event with this id already exists: " + request.getSlug());
        }

        Event event = new Event();
        applyRequest(event, request);
        return toResponse(eventRepository.save(event));
    }

    @Transactional
    public EventResponse update(String slug, EventRequest request) {
        Event event = findEventOrThrow(slug);

        if (!event.getSlug().equals(request.getSlug()) && eventRepository.existsBySlug(request.getSlug())) {
            throw new IllegalArgumentException("Event with this id already exists: " + request.getSlug());
        }

        applyRequest(event, request);
        return toResponse(eventRepository.save(event));
    }

    @Transactional
    public void delete(String slug) {
        Event event = findEventOrThrow(slug);
        eventRepository.delete(event);
    }

    private Event findEventOrThrow(String slug) {
        return eventRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found: " + slug));
    }

    private void applyRequest(Event event, EventRequest request) {
        event.setSlug(request.getSlug().trim());
        event.setTitle(request.getTitle().trim());
        event.setTitleMr(trimToNull(request.getTitleMr()));
        event.setEventDate(request.getDate());
        event.setDateLabel(request.getDateLabel().trim());
        event.setLocation(trimToNull(request.getLocation()));
        event.setPhoto(request.getPhoto().trim());
        event.setPhotoFit(trimToNull(request.getPhotoFit()));
        event.setPhotoPosition(trimToNull(request.getPhotoPosition()));
        event.setDescription(request.getDescription());
    }

    private String trimToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getSlug(),
                event.getTitle(),
                event.getTitleMr(),
                event.getEventDate(),
                event.getDateLabel(),
                event.getLocation(),
                event.getPhoto(),
                event.getPhotoFit(),
                event.getPhotoPosition(),
                List.copyOf(event.getDescription()));
    }
}
