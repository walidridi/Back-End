package com.g4.university.repositories;

import com.g4.university.dto.StudentDto;
import com.g4.university.entities.User;
import com.g4.university.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    boolean existsByEmail(String email) ;

    User findByRole(UserRole userRole);


    Optional<User> findFirstByEmail(String email);


    List<User> findAllByRole(UserRole userRole);

    Optional<User> findFirstById(Long studentId);
}
