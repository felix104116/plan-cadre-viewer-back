package com.project.pcviewer.service;

import com.project.pcviewer.objects.Competence;

import java.util.List;

public interface CompetenceService {
    Competence get(String id);
    List<Competence> getAll();
}
