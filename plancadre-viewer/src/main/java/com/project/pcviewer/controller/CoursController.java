package com.project.pcviewer.controller;

import com.project.pcviewer.objects.Competence;
import com.project.pcviewer.objects.Cours;
import com.project.pcviewer.service.CoursServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@RestController
@RequestMapping("/cours")
@CrossOrigin("*")
public class CoursController {
    private final CoursServiceImpl coursService;

    @Autowired
    public CoursController(CoursServiceImpl coursService){
        this.coursService = coursService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Cours> getCours() {
        return coursService.getCours();

    }

    @RequestMapping(method = RequestMethod.GET, path = "/{annee}/{code}")
    public Cours getSingleCours(@PathVariable Long annee, @PathVariable String code) {
        return coursService.getSingleCours(annee,code);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{code}")
    public Cours getRecentCours(@PathVariable String code){
        return coursService.getRecentCours(code);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/recent")
    public List<Cours> getAllRecentCours(){
        return coursService.getAllRecentCours();
    }

    @RequestMapping(value = "/competence/{id}/{code}", method = RequestMethod.GET)
    public Competence getCompetenceFromCours(@PathVariable String id, @PathVariable String code){
        return coursService.getFromCours(id,code);
    }
    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<Object> exception(NoSuchElementException exception){
        return new ResponseEntity<>("Le cours n'existe pas", HttpStatus.NOT_FOUND);
    }
}
