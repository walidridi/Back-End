package com.g4.university.services.student;

import com.g4.university.dto.EventDto;
import com.g4.university.dto.SingleStudentDto;
import com.g4.university.dto.StudentDto;
import com.g4.university.entities.Club;
import com.g4.university.entities.Event;

import java.util.List;

public interface StudentService {







    SingleStudentDto getStudentById(Long studentId);
    List<StudentDto> getAllStudents();
    List<EventDto> getAllAppliedEventsByStudentId(Long studentId);

    Club CreateClub(Club club);
}
