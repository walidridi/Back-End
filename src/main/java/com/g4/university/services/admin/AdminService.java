package com.g4.university.services.admin;


import com.g4.university.dto.*;
import com.g4.university.entities.Club;

import java.util.List;


public interface AdminService {
    StudentDto postStudent(StudentDto studentDto);

    List<StudentDto> getAllStudents();

    void deleteStudent(Long studentId);

    StudentDto updateStudent(Long studentId, StudentDto studentDto);

    SingleStudentDto getStudentById(Long studentId);

    boolean userExistsByEmail(String email);


    Club CreateClub(Club club);


    TeacherDto postTeacher(TeacherDto teacherDto);
    List<TeacherDto> getAllTeachers();

    SingleTeacherDto getTeachertById(Long teachertId);
    void deleteTeacher(Long studentId);

    TeacherDto updateTeacher(Long teacherId, TeacherDto teacherDto);
}

