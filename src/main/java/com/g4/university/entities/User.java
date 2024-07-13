package com.g4.university.entities;

import com.g4.university.dto.StudentDto;
import com.g4.university.dto.TeacherDto;
import com.g4.university.enums.Gender;
import com.g4.university.enums.UserRole;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //General
    private Long id;
    private String schoolId;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int phone;
    private String address;
    private Gender gender;
    private LocalDate birthDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String biography;
    private String linkedinProfile;
    private String profilePicture;
    private UserRole role;


    //Student

    private String studentClass;

    //Teacher
    private String department;
    private BigDecimal salary;
    private String qualification;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Participant> events = new HashSet<>();

    public StudentDto getStudentDto() {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(id);
        studentDto.setSchoolId(schoolId);
        studentDto.setFirstName(firstName);
        studentDto.setLastName(lastName);
        studentDto.setEmail(email);
        studentDto.setPhone(phone);
        studentDto.setAddress(address);
        studentDto.setGender(gender);
        studentDto.setBirthDate(birthDate);
        studentDto.setStartDate(startDate);
        studentDto.setEndDate(endDate);
        studentDto.setBiography(biography);
        studentDto.setLinkedinProfile(linkedinProfile);
        studentDto.setProfilePicture(profilePicture);
        studentDto.setRole(role);
        studentDto.setStudentClass(studentClass);
        return studentDto;
    }


    public TeacherDto getTeacherDto() {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(id);
        teacherDto.setSchoolId(schoolId);
        teacherDto.setFirstName(firstName);
        teacherDto.setLastName(lastName);
        teacherDto.setEmail(email);
        teacherDto.setPassword(password);
        teacherDto.setPhone(phone);
        teacherDto.setAddress(address);
        teacherDto.setGender(gender);
        teacherDto.setBirthDate(birthDate);
        teacherDto.setStartDate(startDate);
        teacherDto.setEndDate(endDate);
        teacherDto.setBiography(biography);
        teacherDto.setLinkedinProfile(linkedinProfile);
        teacherDto.setProfilePicture(profilePicture);
        teacherDto.setDepartment(department);
        teacherDto.setSalary(salary);
        teacherDto.setQualification(qualification);
        teacherDto.setRole(role);

        return teacherDto;
    }



    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getLinkedinProfile() {
        return linkedinProfile;
    }

    public void setLinkedinProfile(String linkedinProfile) {
        this.linkedinProfile = linkedinProfile;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Set<Participant> getEvents() {
        return events;
    }

    public void setEvents(Set<Participant> events) {
        this.events = events;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {return phone;}

    public void setPhone(int phone) {this.phone = phone;}

    public String getAddress() {return address;}

    public void setAddress(String adress) {this.address = adress;}

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }



}
