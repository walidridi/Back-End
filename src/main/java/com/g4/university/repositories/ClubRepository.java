package com.g4.university.repositories;

import com.g4.university.entities.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClubRepository extends JpaRepository<Club, Integer> {
}
