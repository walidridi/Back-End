package com.g4.university.services.admin;

import com.g4.university.dto.*;
import com.g4.university.entities.Club;
import com.g4.university.entities.Event;
import com.g4.university.entities.User;
import com.g4.university.enums.EventStatus;
import com.g4.university.enums.UserRole;
import com.g4.university.repositories.ClubRepository;
import com.g4.university.repositories.EventRepository;
import com.g4.university.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private  ClubRepository clubRepository;

    public AdminServiceImpl(UserRepository userRepository, EventRepository eventRepository){
        this.userRepository= userRepository;
        this.eventRepository = eventRepository;
    }


    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if (adminAccount == null) {

            User admin= new User();
            admin.setEmail("admin@esprit.tn");
            admin.setName("admin");
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            admin.setRole(UserRole.ADMIN);
            userRepository.save(admin);
        }
    }



    @Override
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    public StudentDto postStudent(StudentDto studentDto) {
            User user = new User();
            BeanUtils.copyProperties(studentDto, user);
            user.setPassword(new BCryptPasswordEncoder().encode(studentDto.getPassword()));
            user.setRole(UserRole.STUDENT);
            User createdUser = userRepository.save(user);
            StudentDto createdStudentDto = new StudentDto();
            createdStudentDto.setId(createdUser.getId());
            createdStudentDto.setEmail(createdUser.getEmail());
            return createdStudentDto;

    }

    @Override
    public List<StudentDto> getAllStudents() {
        return userRepository.findAllByRole(UserRole.STUDENT).stream().map(User::getStudentDto).collect(Collectors.toList());
    }

    @Override
    public void deleteStudent(Long studentId) {
        userRepository.deleteById(studentId);
    }

    @Override
    public StudentDto updateStudent(Long studentId, StudentDto studentDto) {
        Optional<User> optionalUser = userRepository.findFirstById(studentId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(studentDto.getEmail());
            user.setPhone(studentDto.getPhone());
            user.setAddress(studentDto.getAddress()); // Update address if needed
            user.setStudentClass(studentDto.getStudentClass());
            user.setSchoolId(studentDto.getSchoolId()); // Update schoolId if needed
            user.setFirstName(studentDto.getFirstName()); // Update firstName if needed
            user.setLastName(studentDto.getLastName()); // Update lastName if needed
            user.setGender(studentDto.getGender()); // Update gender if needed
            user.setBirthDate(studentDto.getBirthDate()); // Update birthDate if needed
            user.setStartDate(studentDto.getStartDate()); // Update startDate if needed
            user.setEndDate(studentDto.getEndDate()); // Update endDate if needed
            user.setBiography(studentDto.getBiography()); // Update biography if needed
            user.setLinkedinProfile(studentDto.getLinkedinProfile()); // Update linkedinProfile if needed
            user.setProfilePicture(studentDto.getProfilePicture()); // Update profilePicture if needed
            User updatedStudent = userRepository.save(user);

            StudentDto updatedStudentDto = new StudentDto();
            updatedStudentDto.setEmail(updatedStudent.getEmail());
            updatedStudentDto.setPhone(updatedStudent.getPhone());
            updatedStudentDto.setAddress(updatedStudent.getAddress());
            updatedStudentDto.setStudentClass(updatedStudent.getStudentClass());
            updatedStudentDto.setSchoolId(updatedStudent.getSchoolId());
            updatedStudentDto.setFirstName(updatedStudent.getFirstName());
            updatedStudentDto.setLastName(updatedStudent.getLastName());
            updatedStudentDto.setGender(updatedStudent.getGender());
            updatedStudentDto.setBirthDate(updatedStudent.getBirthDate());
            updatedStudentDto.setStartDate(updatedStudent.getStartDate());
            updatedStudentDto.setEndDate(updatedStudent.getEndDate());
            updatedStudentDto.setBiography(updatedStudent.getBiography());
            updatedStudentDto.setLinkedinProfile(updatedStudent.getLinkedinProfile());
            updatedStudentDto.setProfilePicture(updatedStudent.getProfilePicture());
            return updatedStudentDto;
        }
        return null;
    }

    @Override
    public SingleStudentDto getStudentById(Long studentId) {
        Optional<User> optionalUser = userRepository.findById(studentId);
        SingleStudentDto singleStudentDto = new SingleStudentDto();
        optionalUser.ifPresent(user -> singleStudentDto.setStudentDto(user.getStudentDto()));
        return singleStudentDto;
    }

//*************** SERVICE TEACHER ************** //

    @Override
    public TeacherDto postTeacher(TeacherDto teacherDto) {
        User user = new User();
        BeanUtils.copyProperties(teacherDto, user);
        user.setPassword(new BCryptPasswordEncoder().encode(teacherDto.getPassword()));
        user.setRole(UserRole.TEACHER);
        User createdUser = userRepository.save(user);
        TeacherDto createdTeacherDto = new TeacherDto();
        createdTeacherDto.setId(createdUser.getId());
        createdTeacherDto.setEmail(createdUser.getEmail());
        return null;
    }


    @Override
    public List<TeacherDto> getAllTeachers() {
        return userRepository.findAllByRole(UserRole.TEACHER).stream().map(User::getTeacherDto).collect(Collectors.toList());
    }

    @Override
    public SingleTeacherDto getTeachertById(Long teacherId) {
        Optional<User> optionalUser = userRepository.findById(teacherId);
        SingleTeacherDto singleTeacherDto = new SingleTeacherDto();
        optionalUser.ifPresent(user -> singleTeacherDto.setTeacherDto(user.getTeacherDto()));
        return singleTeacherDto;
    }


    @Override
    public void deleteTeacher(Long teacherId) {
        userRepository.deleteById(teacherId);
    }

    @Override
    public TeacherDto updateTeacher(Long teacherId, TeacherDto teacherDto) {
        Optional<User> optionalUser = userRepository.findFirstById(teacherId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setSchoolId(teacherDto.getSchoolId());
            user.setFirstName(teacherDto.getFirstName());
            user.setLastName(teacherDto.getLastName());
            user.setEmail(teacherDto.getEmail());
            user.setPhone(teacherDto.getPhone());
            user.setAddress(teacherDto.getAddress());
            user.setGender(teacherDto.getGender());
            user.setBirthDate(teacherDto.getBirthDate());
            user.setStartDate(teacherDto.getStartDate());
            user.setEndDate(teacherDto.getEndDate());
            user.setBiography(teacherDto.getBiography());
            user.setLinkedinProfile(teacherDto.getLinkedinProfile());
            user.setProfilePicture(teacherDto.getProfilePicture());
            user.setDepartment(teacherDto.getDepartment());
            user.setSalary(teacherDto.getSalary());
            user.setQualification(teacherDto.getQualification());
            user.setRole(teacherDto.getRole());

            User updatedTeacher = userRepository.save(user);

            TeacherDto updatedTeacherDto = new TeacherDto();
            updatedTeacherDto.setId(updatedTeacher.getId());
            updatedTeacherDto.setSchoolId(updatedTeacher.getSchoolId());
            updatedTeacherDto.setFirstName(updatedTeacher.getFirstName());
            updatedTeacherDto.setLastName(updatedTeacher.getLastName());
            updatedTeacherDto.setEmail(updatedTeacher.getEmail());
            updatedTeacherDto.setPhone(updatedTeacher.getPhone());
            updatedTeacherDto.setAddress(updatedTeacher.getAddress());
            updatedTeacherDto.setGender(updatedTeacher.getGender());
            updatedTeacherDto.setBirthDate(updatedTeacher.getBirthDate());
            updatedTeacherDto.setStartDate(updatedTeacher.getStartDate());
            updatedTeacherDto.setEndDate(updatedTeacher.getEndDate());
            updatedTeacherDto.setBiography(updatedTeacher.getBiography());
            updatedTeacherDto.setLinkedinProfile(updatedTeacher.getLinkedinProfile());
            updatedTeacherDto.setProfilePicture(updatedTeacher.getProfilePicture());
            updatedTeacherDto.setDepartment(updatedTeacher.getDepartment());
            updatedTeacherDto.setSalary(updatedTeacher.getSalary());
            updatedTeacherDto.setQualification(updatedTeacher.getQualification());
            updatedTeacherDto.setRole(updatedTeacher.getRole());

            return updatedTeacherDto;
        }
        return null;
    }









    @Override
    public Club CreateClub(Club club) {
        return clubRepository.save(club);
    }


}
