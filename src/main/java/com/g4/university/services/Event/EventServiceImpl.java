package com.g4.university.services.Event;

import com.g4.university.dto.EventDto;
import com.g4.university.dto.SingleEventDto;
import com.g4.university.dto.SingleStudentDto;
import com.g4.university.entities.Event;
import com.g4.university.entities.Participant;
import com.g4.university.entities.User;
import com.g4.university.enums.EventStatus;
import com.g4.university.repositories.EventRepository;
import com.g4.university.repositories.ParticipantRepository;
import com.g4.university.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Override
    //@Transactional
    public Event createEvent(EventDto eventDto, Long userId) {
        User student = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));

        Event event = new Event();
        event.setNameEvent(eventDto.getNameEvent());
        event.setDescription(eventDto.getDescription());
        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());
        event.setLocation(eventDto.getLocation());
        event.setCapacity(eventDto.getCapacity());
        event.setOrganizer(eventDto.getOrganizer());
        event.setCategory(eventDto.getCategory());
        event.setImage(eventDto.getImage());
        event.setTags(eventDto.getTags());
        event.setEventStatus(EventStatus.Pending);
        event.setUser(student);
        return eventRepository.save(event);
    }



    private EventDto convertToDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setIdEvent(event.getIdEvent());
        eventDto.setNameEvent(event.getNameEvent());
        eventDto.setDescription(event.getDescription());
        eventDto.setStartDate(event.getStartDate());
        eventDto.setEndDate(event.getEndDate());
        eventDto.setLocation(event.getLocation());
        eventDto.setCapacity(event.getCapacity());
        eventDto.setOrganizer(event.getOrganizer());
        eventDto.setCategory(event.getCategory());
        eventDto.setImage(event.getImage());
        eventDto.setTags(event.getTags());
        eventDto.setEventStatus(event.getEventStatus());
        eventDto.setUserid(event.getUser().getId()); // the user ID
        return eventDto;
    }


    @Override
    public EventDto getEventDtoById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));
        return convertToDto(event);
    }



    @Override
    public List<Event> getEventsCreatedByStudent(Long userId) {
        // Retrieve events created by the student identified by userId

        User student = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));
        return eventRepository.findByUser(student);
    }

    @Override
    public List<EventDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<Event> getAppliedEvents() {

        return eventRepository.findByEventStatus(EventStatus.Pending);
    }


    @Override
    public Event updateEvent(Long eventId, EventDto eventDto, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to update this event");
        }
        event.setNameEvent(eventDto.getNameEvent());
        event.setDescription(eventDto.getDescription());
        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());
        event.setLocation(eventDto.getLocation());
        event.setCapacity(eventDto.getCapacity());
        event.setOrganizer(eventDto.getOrganizer());
        event.setCategory(eventDto.getCategory());
        event.setImage(eventDto.getImage());
        event.setTags(eventDto.getTags());
        event.setEventStatus(eventDto.getEventStatus());
        return eventRepository.save(event);
    }

    @Override
    public Event updateEventAdmin(Long eventId, EventDto eventDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        event.setNameEvent(eventDto.getNameEvent());
        event.setDescription(eventDto.getDescription());
        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());
        event.setLocation(eventDto.getLocation());
        event.setCapacity(eventDto.getCapacity());
        event.setOrganizer(eventDto.getOrganizer());
        event.setCategory(eventDto.getCategory());
        event.setImage(eventDto.getImage());
        event.setTags(eventDto.getTags());
        event.setEventStatus(eventDto.getEventStatus());

        return eventRepository.save(event);
    }


        @Override
    public Event updateEventStatus(Long eventId, EventStatus eventStatus) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        event.setEventStatus(eventStatus);
        return eventRepository.save(event);
    }


    @Override
    public void deleteEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this event");
        }

        eventRepository.delete(event);
    }

    @Override
    public void deleteEventAdmin(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new IllegalArgumentException("Event not found");
        }
        eventRepository.deleteById(eventId);
    }


    @Override
    @Transactional
    public Event addParticipantToEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (event.getParticipants().stream().anyMatch(participant -> participant.getUser().getId().equals(userId))) {
            throw new IllegalArgumentException("User is already a participant in this event");
        }

        if (event.getParticipants().size() >= event.getCapacity()) {
            throw new IllegalStateException("Event capacity is full, cannot add more participants");
        }

        Participant participant = new Participant();
        participant.setEvent(event);
        participant.setUser(user);

        event.getParticipants().add(participant);

        return eventRepository.save(event);
    }



    public Event removeParticipantFromEvent(Long eventId, Long userId, Long authenticatedUserId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        // Check if the authenticated user is the creator of the event
        if (!event.getUser().getId().equals(authenticatedUserId)) {
            throw new AccessDeniedException("Only the event creator can remove participants");
        }

        // Remove the participant from the event
        event.getParticipants().removeIf(participant -> participant.getUser().getId().equals(userId));

        // Update the event in the repository
        return eventRepository.save(event);
    }


}

