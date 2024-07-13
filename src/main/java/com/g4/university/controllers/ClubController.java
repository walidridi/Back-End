package com.g4.university.controllers;
import com.g4.university.entities.Club;
import com.g4.university.services.modules.ClubServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
//@RequestMapping("/club")
public class ClubController {

    private final ClubServiceImpl clubService;

    public Club add(@RequestBody Club club){
        return clubService.Create(club);
    }
    public List<Club> getAll(){
        return clubService.Retrieve();
    }
}
