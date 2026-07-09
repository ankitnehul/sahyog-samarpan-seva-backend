package com.sahyogsamarpan.seva.event.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EventRequest {

    @NotBlank
    @Size(max = 120)
    @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "use lowercase letters, numbers and hyphens only")
    private String slug;

    @NotBlank
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String titleMr;

    @NotNull
    private LocalDate date;

    @NotBlank
    @Size(max = 80)
    private String dateLabel;

    @Size(max = 255)
    private String location;

    @NotBlank
    @Size(max = 500)
    private String photo;

    @Pattern(regexp = "cover|contain", message = "must be cover or contain")
    private String photoFit;

    @Size(max = 80)
    private String photoPosition;

    @NotEmpty
    private List<@NotBlank @Size(max = 2000) String> description;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
}
