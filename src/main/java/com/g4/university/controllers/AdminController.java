package com.g4.university.controllers;


import com.g4.university.dto.*;
import com.g4.university.entities.Club;
import com.g4.university.entities.Event;
import com.g4.university.services.Event.EventService;
import com.g4.university.services.admin.AdminService;
import com.g4.university.services.student.StudentService;
import com.g4.university.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final EventService eventService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AdminController(AdminService adminService, EventService eventService, JwtUtil jwtUtil) {
        this.adminService = adminService;
        this.eventService = eventService;
        this.jwtUtil = jwtUtil;
    }



//*************** CRUD EVENT ************** //

    @GetMapping("/events/all")
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> allEvents = eventService.getAllEvents();
        return ResponseEntity.ok(allEvents);
    }



    @GetMapping("/events/applied")
    public ResponseEntity<List<Event>> getAppliedEvents() {
        List<Event> pendingEvents = eventService.getAppliedEvents();
        return ResponseEntity.ok(pendingEvents);

    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<SingleEventDto> getEventById(@PathVariable Long eventId) {
        EventDto eventDto = eventService.getEventDtoById(eventId);

        if (eventDto != null) {
            SingleEventDto singleEventDto = new SingleEventDto();
            singleEventDto.setEventDto(eventDto);
            return ResponseEntity.ok(singleEventDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/events/{eventId}/status")
    public ResponseEntity<Event> updateEventStatus(@PathVariable Long eventId, @RequestBody EventStatusUpdateDto eventStatusUpdateDto) {
        Event updatedEvent = eventService.updateEventStatus(eventId, eventStatusUpdateDto.getEventStatus());
        return ResponseEntity.ok(updatedEvent);
    }

    @PutMapping("/events/{eventId}")
    public ResponseEntity<Event> updateEventAdmin(@PathVariable Long eventId, @RequestBody EventDto eventDto) {
        Event updatedEvent = eventService.updateEventAdmin(eventId, eventDto);
        return ResponseEntity.ok(updatedEvent);
    }


    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> deleteEventAdmin(@PathVariable Long eventId) {
        eventService.deleteEventAdmin(eventId);
        return ResponseEntity.noContent().build();
    }



//*************** CRUD STUDENT ************** //

    @PostMapping("/student")
    public ResponseEntity<?> addStudent(@RequestBody StudentDto studentDto) {
        try {
            if (adminService.userExistsByEmail(studentDto.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body("Student with this email already exists! Try to login.");
            }

            StudentDto createdStudentDto = adminService.postStudent(studentDto);
            if (createdStudentDto == null) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Problem creating student.");
            }

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdStudentDto);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred.");
        }
    }


    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getAllStudents(){
        List<StudentDto> allStudents = adminService.getAllStudents();
        return ResponseEntity.ok(allStudents);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<SingleStudentDto> getStudentById(@PathVariable Long studentId  ){
        SingleStudentDto singleStudentDto = adminService.getStudentById(studentId);
        if (singleStudentDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(singleStudentDto);
    }

    @DeleteMapping("/student/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId  ){
        adminService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/student/{studentId}")
    public ResponseEntity<?> updateStudent (@PathVariable Long studentId, @RequestBody StudentDto studentDto){
        StudentDto createdStudentDto =  adminService.updateStudent(studentId, studentDto);
        if (createdStudentDto == null)
            return new ResponseEntity<>("probleme!", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudentDto);
    }








//*************** CRUD TEACHER ************** //

    @PostMapping("/teacher")
    public ResponseEntity<?> postTeacher(@RequestBody TeacherDto teacherDto) {
        try {
            if (adminService.userExistsByEmail(teacherDto.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body("Teacher with this email already exists! Try to login.");
            }

            TeacherDto createdTeacherDto = adminService.postTeacher(teacherDto);
            if (createdTeacherDto == null) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Problem creating Teacher.");
            }

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdTeacherDto);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred.");
        }
    }


    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherDto>> getAllTeachers(){
        List<TeacherDto> allTeachers = adminService.getAllTeachers();
        return ResponseEntity.ok(allTeachers);
    }


    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<SingleTeacherDto> getTeachertById(@PathVariable Long teacherId){
        SingleTeacherDto singleTeacherDto = adminService.getTeachertById(teacherId);
        if (singleTeacherDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(singleTeacherDto);
    }

    @DeleteMapping("/teacher/{teacherId}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long teacherId){
        adminService.deleteTeacher(teacherId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/teacher/{teacherId}")
    public ResponseEntity<?> updateTeacher (@PathVariable Long teacherId, @RequestBody TeacherDto teacherDto){
        TeacherDto createdTeacherDto =  adminService.updateTeacher(teacherId, teacherDto);
        if (createdTeacherDto == null)
            return new ResponseEntity<>("probleme!", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacherDto);
    }









//*************** CRUD CLUB ************** //

    @Autowired
    private ClubController clubController;

    @PostMapping("/club/create-club")
    public Club createClub(@RequestBody Club club){
        return clubController.add(club);
    }

    @GetMapping("/club/get-all-clubs")
    public List<Club> getAllClubs(){
        return clubController.getAll();
    }
}



