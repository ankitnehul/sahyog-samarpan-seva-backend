package com.sahyogsamarpan.seva.event.dto;

import java.time.LocalDate;
import java.util.List;

public record EventResponse(
        String id,
        String title,
        String titleMr,
        LocalDate date,
        String dateLabel,
        String location,
        String photo,
        String photoFit,
        String photoPosition,
        List<String> description) {
}
