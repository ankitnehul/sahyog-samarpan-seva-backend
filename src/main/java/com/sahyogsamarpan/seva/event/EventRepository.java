package com.sahyogsamarpan.seva.event;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByOrderByEventDateDesc();

    Optional<Event> findBySlug(String slug);

    boolean existsBySlug(String slug);
}
