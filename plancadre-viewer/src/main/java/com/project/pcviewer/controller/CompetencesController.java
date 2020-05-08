package com.project.pcviewer.controller;

import com.project.pcviewer.objects.Competence;
import com.project.pcviewer.service.CompetenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/competences")
@CrossOrigin("*")
public class CompetencesController {
    private final CompetenceService competenceService;

    @Autowired
    public CompetencesController(CompetenceService competenceService){
        this.competenceService = competenceService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Competence> getCompetences(){
        return competenceService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Competence getCompetence(@PathVariable String id){
        return competenceService.get(id);
    }

}
