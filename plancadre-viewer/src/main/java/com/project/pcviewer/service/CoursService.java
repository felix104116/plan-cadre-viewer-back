package com.project.pcviewer.service;

import com.project.pcviewer.objects.Competence;
import com.project.pcviewer.objects.Cours;

import java.util.List;

public interface CoursService {
    List<Cours> getCours();
    Cours getSingleCours(Long annee, String code);
    List<Cours> getAllRecentCours();
    Cours getRecentCours(String code);

    Competence getFromCours(String id, String code);
}
