package com.g4.university.dto;


import com.g4.university.enums.Gender;
import com.g4.university.enums.UserRole;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TeacherDto {

    private Long id;
    private String schoolId;
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
    private String department;
    private BigDecimal salary;
    private String qualification;
    private UserRole role;
}
