package com.g4.university.services.modules;


import com.g4.university.entities.Club;
import com.g4.university.repositories.ClubRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClubServiceImpl implements IService<Club> {

    private final ClubRepository clubRepository;

    @Override
    public Club Create(Club session) {
        return clubRepository.save(session);
    }

    @Override
    public List<Club> Retrieve() {
        return clubRepository.findAll();
    }

    @Override
    public void Delete(int id) {

    }
}
