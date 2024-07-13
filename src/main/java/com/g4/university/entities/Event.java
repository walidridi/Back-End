package com.g4.university.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.g4.university.dto.EventDto;
import com.g4.university.enums.EventStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "event")
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvent;
    private String nameEvent;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private Long capacity;
    private String organizer;
    private String category;
    private String image;
    private String tags;
    private EventStatus eventStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;


    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Participant> participants = new HashSet<>();

    public EventDto getEventDto() {
        EventDto eventDto = new EventDto();
        eventDto.setIdEvent(idEvent);
        eventDto.setNameEvent(nameEvent);
        eventDto.setDescription(description);
        eventDto.setStartDate(startDate);
        eventDto.setEndDate(endDate);
        eventDto.setLocation(location);
        eventDto.setCapacity(capacity);
        eventDto.setOrganizer(organizer);
        eventDto.setCategory(category);
        eventDto.setImage(image);
        eventDto.setTags(tags);
        eventDto.setEventStatus(eventStatus);
        eventDto.setUserid(user.getId());
        return eventDto;
    }
}