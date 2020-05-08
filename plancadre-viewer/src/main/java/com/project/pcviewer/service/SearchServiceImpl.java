package com.project.pcviewer.service;

import com.project.pcviewer.objects.*;
import com.project.pcviewer.objects.V1.CompetenceV1;
import com.project.pcviewer.objects.V1.SearchV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    private final CoursService coursService;
    private final CompetenceService competenceService;

    @Autowired
    public SearchServiceImpl(CoursService coursService, CompetenceService competenceService) {
        this.coursService = coursService;
        this.competenceService = competenceService;
    }

    public SearchV1 toSearchV1(Competence competence){
        SearchV1 search = new SearchV1();
        search.setCode(competence.getCodeCompetence());
        search.setTitre(competence.getEnonce());
        search.setType("competences");
        search.setId(UUID.randomUUID().toString());
        return search;
    }

    public SearchV1 toSearchV1(Cours cours){
        SearchV1 search = new SearchV1();
        search.setCode(cours.getCode());
        search.setTitre(cours.getTitre());
        search.setType("cours");
        search.setId(UUID.randomUUID().toString());
        return search;
    }

    public List<SearchV1> searchByKeyword(String keyword){
        List<SearchV1> searches = new ArrayList<>();
        searches.addAll(getCours(keyword));
        searches.addAll(getCompetences(keyword));
        return searches;
    }

    @Override
    public List<SearchV1> getAll() {
        List<SearchV1> searches = new ArrayList<>();
        searches.addAll(coursService.getCours().stream()
                .map(this::toSearchV1)
                .collect(Collectors.toList()));
        searches.addAll(competenceService.getAll().stream()
                .map(this::toSearchV1)
                .collect(Collectors.toList()));
        return searches;
    }

    private boolean isValid(String value, String keyword){
        if(value != null)
            return value.contains(keyword);
        return false;
    }

    private List<SearchV1> getCompetences(String keyword){
        List<Competence> competences = competenceService.getAll();
        List<Competence> filtered = new ArrayList<>();
        for(Competence c: competences){
            List<ElementCompetence> elements = c.getElements();
            if(isValid(c.getCodeCompetence(),keyword) || isValid(c.getEnonce(),keyword)|| c.getContextes().stream().anyMatch(s->isValid(s,keyword))||
                    elements.stream().anyMatch(e -> isValid(e.getDescription(),keyword))||elements.stream().flatMap(e -> e.getCriteres().stream()).anyMatch(s -> isValid(s,keyword))){
                filtered.add(c);
            }
        }
        return filtered.stream()
                .map(this::toSearchV1)
                .collect(Collectors.toList());
    }

    private List<SearchV1> getCours(String keyword){
        List<Cours> cours = coursService.getCours();
        List<Cours> filtered = new ArrayList<>();
        for (Cours c : cours){
            if(isValid(c.getCode(),keyword) || isValid(c.getTitre(),keyword) || isValid(c.getObjectifIntegrateur(),keyword)
            || isValid(c.getPresentation(),keyword) || isValid(c.getManuelObligatoire(),keyword) || isValid(c.getObjectifApprentissage(),keyword)
            || isValid(c.getContribution(),keyword) || isValid(String.valueOf(c.getAnnee()),keyword)){
                filtered.add(c);
                continue;
            }
            List<CompetenceV1> comp = c.getCompetences();
            if(comp.stream().anyMatch(c1 -> isValid(c1.getEnonce(),keyword))||
                comp.stream().flatMap(competenceV1 -> competenceV1.getElements().stream()).anyMatch(s -> isValid(s,keyword))) {
                filtered.add(c);
                continue;
            }
            List<Objectif> obj = c.getObjectifs();
            if(obj.stream().anyMatch(o -> isValid(o.getCompetence(),keyword)) || obj.stream().flatMap(o -> o.getElements().stream()).anyMatch(s -> isValid(s,keyword))){
                filtered.add(c);
                continue;
            }
            if(isValid(c.getProgramme().getNom(),keyword) || isValid(c.getProgramme().getCode(),keyword)) {
                filtered.add(c);
                continue;
            }
            EvaluationApprentissage e = c.getEvaluationApprentissage();
            List<Evaluation> ev = e.getElements();
            if(isValid(e.getDescription(),keyword) || isValid(e.getDetailLangue(),keyword) || isValid(e.getDetailPourcentage(),keyword)
            || isValid(e.getTitre(), keyword) || ev.stream().anyMatch(evaluation -> isValid(evaluation.getDescription(),keyword)) ||
            ev.stream().flatMap(evaluation -> evaluation.getDetails().stream()).anyMatch(s -> isValid(s,keyword)) ||
            e.getCriteres().stream().anyMatch(s -> isValid(s,keyword))|| c.getRecommandations().stream().anyMatch(s -> isValid(s,keyword))){
                filtered.add(c);
            }
        }
        return filtered.stream()
                .map(this::toSearchV1)
                .collect(Collectors.toList());
    }
}
