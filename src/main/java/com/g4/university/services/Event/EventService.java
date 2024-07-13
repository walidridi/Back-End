package com.g4.university.services.Event;

import com.g4.university.dto.EventDto;
import com.g4.university.dto.SingleStudentDto;
import com.g4.university.entities.Event;
import com.g4.university.enums.EventStatus;

import java.util.List;

public interface EventService {
    Event createEvent(EventDto eventDto, Long userId);
    List<Event> getEventsCreatedByStudent(Long userId);
    List<EventDto> getAllEvents();
    List<Event> getAppliedEvents();
    EventDto getEventDtoById(Long eventId);

    Event updateEvent(Long eventId, EventDto eventDto, Long userId);
    Event updateEventAdmin(Long eventId, EventDto eventDto);
    void deleteEvent(Long eventId, Long userId);
    void deleteEventAdmin(Long eventId);
    Event updateEventStatus(Long eventId, EventStatus eventStatus);



    Event addParticipantToEvent(Long eventId, Long userId);
    Event removeParticipantFromEvent(Long eventId, Long userId, Long authenticatedUserId);





}