package com.g4.university.repositories;

import com.g4.university.entities.Event;
import com.g4.university.entities.Participant;
import com.g4.university.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {


}
