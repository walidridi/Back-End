package com.g4.university.dto;

import com.g4.university.enums.EventStatus;

public class EventStatusUpdateDto {
    private EventStatus eventStatus;

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }
}
