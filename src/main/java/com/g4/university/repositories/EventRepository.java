package com.g4.university.repositories;

import com.g4.university.entities.Event;
import com.g4.university.entities.User;
import com.g4.university.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByUserId(Long studentId);

    List<Event> findByUser(User student);
    List<Event> findByEventStatus(EventStatus eventStatus);


}
