package com.g4.university.controllers;

import com.g4.university.dto.EventDto;
import com.g4.university.dto.SingleStudentDto;
import com.g4.university.dto.StudentDto;
import com.g4.university.entities.Club;
import com.g4.university.entities.Event;

import com.g4.university.repositories.EventRepository;
import com.g4.university.services.Event.EventService;
import com.g4.university.services.student.StudentService;
import com.g4.university.utils.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;
    private final EventService eventService;
    private final JwtUtil jwtUtil;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    public StudentController(StudentService studentService, EventService eventService, JwtUtil jwtUtil) {
        this.studentService = studentService;
        this.eventService = eventService;
        this.jwtUtil = jwtUtil;
    }


    private Long getUserIdFromAuthentication(Authentication authentication) {
        return jwtUtil.extractUserId(authentication);
    }



    @PostMapping("/create-event")
    public ResponseEntity<?> createEvent(@RequestBody EventDto eventDto, Authentication authentication) {
        // Extract student's ID from authentication object
        Long userId = getUserIdFromAuthentication(authentication);

        // Create the event using the StudentService
        Event createdEvent = eventService.createEvent(eventDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }


    @GetMapping("/events/created")
    public ResponseEntity<List<Event>> getEventsCreatedByStudent(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);

        // Retrieve events created by the student
        List<Event> events = eventService.getEventsCreatedByStudent(userId);

        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/all")
    public ResponseEntity<List<EventDto>> getAllEvents() {
        // Retrieve all events from the system
        List<EventDto> events = eventService.getAllEvents();

        return ResponseEntity.ok(events);
    }



    @PutMapping("/events/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long eventId, @RequestBody EventDto eventDto, Authentication authentication) {
        Long userId = jwtUtil.extractUserId(authentication);
        Event updatedEvent = eventService.updateEvent(eventId, eventDto, userId);
        return ResponseEntity.ok(updatedEvent);
    }


    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId, Authentication authentication) {
        Long userId = jwtUtil.extractUserId(authentication);
        eventService.deleteEvent(eventId, userId);
        return ResponseEntity.noContent().build();
    }





//*************** CRUD EVENT PARTICIPANT ************** //



    @PostMapping("/events/{eventId}/participants")
    public ResponseEntity<Event> addParticipantToEvent(@PathVariable Long eventId, Authentication authentication) {
        Long userId = jwtUtil.extractUserId(authentication);

        Event updatedEvent = eventService.addParticipantToEvent(eventId, userId);

        return ResponseEntity.ok(updatedEvent);
    }


    @DeleteMapping("/events/{eventId}/participants/{userId}")
    public ResponseEntity<Event> removeParticipantFromEvent(@PathVariable Long eventId,
                                                            @PathVariable Long userId,
                                                            Authentication authentication) {
        // Extract authenticated student's ID from authentication object
        Long authenticatedUserId = getUserIdFromAuthentication(authentication);

        // Remove participant from the event
        Event updatedEvent = eventService.removeParticipantFromEvent(eventId, userId, authenticatedUserId);

        return ResponseEntity.ok(updatedEvent);
    }


    //*************** CRUD STUDENT ************** //

    @GetMapping("/{studentId}")
    public ResponseEntity<SingleStudentDto> getStudentById(@PathVariable Long studentId  ){
        SingleStudentDto singleStudentDto = studentService.getStudentById(studentId);
        if (singleStudentDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(singleStudentDto);
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getAllStudents(){
        List<StudentDto> allStudents = studentService.getAllStudents();
        return ResponseEntity.ok(allStudents);
    }



    //@PostMapping("/create-event")
  //  public ResponseEntity<?> createEvent(@RequestBody EventDto eventDto){
    //    EventDto submittedEventDto = studentService.createEvent(eventDto);
     //   if(submittedEventDto == null)
      //      return new ResponseEntity<>("Somthing went wrong", HttpStatus.BAD_REQUEST);
      //  return ResponseEntity.status(HttpStatus.CREATED).body(submittedEventDto);
    //}




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


