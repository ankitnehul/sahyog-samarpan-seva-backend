package com.sahyogsamarpan.seva.event;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** URL-friendly id, matches frontend WorkEvent.id (e.g. maharashtra-day-2026). */
    @Column(nullable = false, unique = true, length = 120)
    private String slug;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 255)
    private String titleMr;

    @Column(nullable = false)
    private LocalDate eventDate;

    @Column(nullable = false, length = 80)
    private String dateLabel;

    @Column(length = 255)
    private String location;

    @Column(nullable = false, length = 500)
    private String photo;

    @Column(length = 20)
    private String photoFit;

    @Column(length = 80)
    private String photoPosition;

    @ElementCollection
    @CollectionTable(name = "event_paragraphs", joinColumns = @JoinColumn(name = "event_id"))
    @OrderColumn(name = "paragraph_order")
    @Column(name = "paragraph", nullable = false, length = 2000)
    private List<String> description = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    public Event() {
    }

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleMr() {
        return titleMr;
    }

    public void setTitleMr(String titleMr) {
        this.titleMr = titleMr;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getDateLabel() {
        return dateLabel;
    }

    public void setDateLabel(String dateLabel) {
        this.dateLabel = dateLabel;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoFit() {
        return photoFit;
    }

    public void setPhotoFit(String photoFit) {
        this.photoFit = photoFit;
    }

    public String getPhotoPosition() {
        return photoPosition;
    }

    public void setPhotoPosition(String photoPosition) {
        this.photoPosition = photoPosition;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
