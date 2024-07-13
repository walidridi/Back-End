package com.g4.university.services.student;


import com.g4.university.dto.EventDto;
import com.g4.university.dto.SingleStudentDto;
import com.g4.university.dto.StudentDto;
import com.g4.university.entities.Club;
import com.g4.university.entities.Event;
import com.g4.university.entities.User;
import com.g4.university.enums.EventStatus;
import com.g4.university.enums.UserRole;
import com.g4.university.repositories.ClubRepository;
import com.g4.university.repositories.EventRepository;
import com.g4.university.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Autowired
    public StudentServiceImpl(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }





    ///**************************





    @Override
    public SingleStudentDto getStudentById(Long studentId) {
        Optional<User> optionalUser = userRepository.findById(studentId);
        SingleStudentDto singleStudentDto = new SingleStudentDto();
        optionalUser.ifPresent(user -> singleStudentDto.setStudentDto(user.getStudentDto()));
        return singleStudentDto;
    }



    @Override
    public List<StudentDto> getAllStudents() {
        return userRepository.findAllByRole(UserRole.STUDENT).stream().map(User::getStudentDto).collect(Collectors.toList());
    }



    @Override
    public List<EventDto> getAllAppliedEventsByStudentId(Long studentId) {
        return eventRepository.findAllByUserId(studentId).stream().map(Event::getEventDto).collect(Collectors.toList());
    }



    @Override
    public Club CreateClub(Club club) {
        return null;
    }

}
