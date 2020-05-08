package com.project.pcviewer.service;

import com.project.pcviewer.objects.Competence;
import com.project.pcviewer.objects.Cours;
import com.project.pcviewer.objects.ElementCompetence;
import com.project.pcviewer.objects.V1.CompetenceV1;
import com.project.pcviewer.utils.JSONArrayUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CompetenceServiceImpl implements CompetenceService{
    private static final Logger LOGGER = LoggerFactory.getLogger(CompetenceServiceImpl.class);
    private final JSONParser parser = new JSONParser();
    private List<Competence> cache;


    @Override
    public Competence get(String id) {
        Optional<Competence> competence = cache.stream()
                .filter(c -> id.equals(c.getCodeCompetence()))
                .findAny();
        if(competence.isEmpty()) throw new NoSuchElementException("Il n'y a pas de competence avec ce code de competence " + id);
        return competence.get();
    }

    @Override
    public List<Competence> getAll() {
        return cache;
    }

    @SuppressWarnings("unchecked")
    public Competence jsonToCompetence(JSONObject json){
        Competence competence = new Competence();
        competence.setCodeCompetence((String) json.get("CodeCompetence"));
        competence.setContextes(JSONArrayUtil.jsonArrayToList((JSONArray)json.get("ContexteDeRealisation")));
        competence.setEnonce((String) json.get("EnonceCompetence"));
        List<JSONObject> objects = ((List<JSONObject>) json.get("Competences"));
        List<ElementCompetence> competences = new ArrayList<>();
        for(JSONObject obj : objects){
            ElementCompetence elementCompetence = new ElementCompetence();
            elementCompetence.setId((Long) obj.get("Id"));
            elementCompetence.setDescription((String) obj.get("Description"));
            elementCompetence.setCriteres(JSONArrayUtil.jsonArrayToList((JSONArray) obj.get("Criteres")));
            competences.add(elementCompetence);
        }
        competence.setElements(competences);
        return competence;
    }


    @Scheduled(fixedRate = 20 * 1000L)
    public void UpdateCache(){
        List<Competence> competences = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(CoursServiceImpl.jsonPath+"/competences"))) {
            List<String> result = walk.filter(Files::isRegularFile)
                    .map(Path::toString).collect(Collectors.toList());
            for(String s: result) {
                try (FileReader reader = new FileReader(s)) {
                    competences.add(jsonToCompetence((JSONObject)parser.parse(reader)));
                } catch (FileNotFoundException e) {
                    LOGGER.error("Le fichier suivant n'a pas ete trouve: " + s);
                } catch (IOException e) {
                    LOGGER.error("Il y a eu une erreur lors de la lecteur du fichier: " + s);
                }  catch(NumberFormatException e){
                    LOGGER.error(e.getMessage() + e);
                } catch (ParseException e) {
                    LOGGER.error("Il y a eu un erreur lors de la conversion du fichier. Verifiez le contenu du fichier " + s);
                }
            }
            this.cache = competences;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
