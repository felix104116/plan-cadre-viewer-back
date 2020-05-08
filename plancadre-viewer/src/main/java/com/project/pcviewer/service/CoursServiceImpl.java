package com.project.pcviewer.service;

import com.project.pcviewer.objects.*;
import com.project.pcviewer.objects.V1.CompetenceV1;
import com.project.pcviewer.utils.JSONArrayUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
@EnableScheduling
public class CoursServiceImpl implements CoursService {
    public List<Cours> cache = new ArrayList<>();
    public static final String jsonPath = "./JSON";
    private final JSONParser parser = new JSONParser();
    private final Logger LOGGER = LoggerFactory.getLogger(CoursServiceImpl.class);
    private final CompetenceService competenceService;

    @Autowired
    public CoursServiceImpl(CompetenceService competenceService){
        this.competenceService = competenceService;
    }

    public List<CompetenceV1> getCompetencesV1(List<String> ids){
        List<CompetenceV1> list = new ArrayList<>();
        for(String id : ids){
            Competence competence = competenceService.get(id);
            CompetenceV1 competenceV1 = new CompetenceV1();
            competenceV1.setId(competence.getCodeCompetence());
            competenceV1.setEnonce(competence.getEnonce());
            list.add(competenceV1);
        }
        return list;
    }

    public String getCompetenceEnonce(String id){
        return competenceService.get(id).getEnonce();
    }

    @Override
    public Competence getFromCours(String id, String code) {
        Competence competence = new Competence();
        List<String> competences = getRecentCours(code).getCompetences().stream()
                .flatMap(competenceV1 -> competenceV1.getElements().stream())
                .collect(Collectors.toList());
        Competence full = competenceService.get(id);
        competence.setEnonce(full.getEnonce());
        competence.setContextes(full.getContextes());
        competence.setCodeCompetence(full.getCodeCompetence());
        competence.setElements(full.getElements().stream()
                .filter(comp -> competences.contains(comp.getId().toString()))
                .collect(Collectors.toList()));
        return competence;
    }

    public Cours jsonToCours(JSONObject json, File file){
        Cours cours = new Cours();
        Programme programme = new Programme();
        programme.setNom((String) json.get("Programme"));
        programme.setCode((String) json.get("CodeProgramme"));
        ArrayList<CompetenceV1> comp = new ArrayList<>();
        JSONArray competences = (JSONArray) json.get("Competences");
        if(competences != null) {
            for (Object o : competences) {
                if(o instanceof JSONObject){
                    CompetenceV1 competenceV1 = new CompetenceV1();
                    JSONObject obj = (JSONObject) o;
                    competenceV1.setId((String)obj.get("Code"));
                    competenceV1.setElements(JSONArrayUtil.jsonArrayToList((JSONArray)obj.get("Elements")));
                    competenceV1.setEnonce(getCompetenceEnonce(competenceV1.getId()));
                    comp.add(competenceV1);
                }
            }
        }
        cours.setCompetences(comp);
        JSONArray succ = (JSONArray)json.get("Successeurs");
        List<AutreCours> successeurs = new ArrayList<>();
        if(succ != null) {
            for (Object o : succ) {
                if (o instanceof JSONObject) {
                    AutreCours autreCours = new AutreCours();
                    JSONObject successeur = (JSONObject) o;
                    autreCours.setCommentaire((String)successeur.get("Commentaire"));
                    autreCours.setCodeCours((String)successeur.get("Code"));
                    successeurs.add(autreCours);
                }
            }
        }
        cours.setSuccesseurs(successeurs);

        JSONArray pre = (JSONArray)json.get("Prealables");
        List<AutreCours> prealables = new ArrayList<>();
        if(pre != null) {
            for (Object o : pre) {
                if (o instanceof JSONObject) {
                    AutreCours autreCours = new AutreCours();
                    JSONObject prealable = (JSONObject) o;
                    autreCours.setCommentaire((String)prealable.get("Commentaire"));
                    autreCours.setCodeCours((String) prealable.get("Code"));
                    prealables.add(autreCours);
                }
            }
        }
        cours.setPrealables(prealables);

        cours.setRecommandations(JSONArrayUtil.jsonArrayToList((JSONArray) json.get("Recommandations")));
        cours.setAnnee(Integer.parseInt((String)json.get("Annee")));
        cours.setProgramme(programme);
        cours.setId(UUID.randomUUID().toString());
        cours.setCode((String) json.get("Code"));
        cours.setContribution((String) json.get("Contribution"));
        cours.setTitre((String) json.get("Titre"));
        cours.setSession(Integer.parseInt((String)json.get("Session")));
        cours.setNbHeureTheorie(Integer.parseInt((String)json.get("Theorie")));
        cours.setNbHeurePratique(Integer.parseInt((String)json.get("Pratique")));
        cours.setNbHeurePersonnel(Integer.parseInt((String)json.get("Personnel")));
        EvaluationApprentissage evaluationApprentissage = new EvaluationApprentissage();
        JSONObject obj = (JSONObject)json.get("EvaluationDesApprentissages");
        evaluationApprentissage.setDescription((String)obj.get("Description"));
        List<Evaluation> evaluationList = new ArrayList<>();
        JSONArray evaluations = (JSONArray)obj.get("Evaluations");
        if(evaluations != null) {
            for (Object o : evaluations) {
                if (o instanceof JSONObject) {
                    JSONObject evaluation = (JSONObject) o;
                    Evaluation ev = new Evaluation();
                    ev.setId(UUID.randomUUID().toString());
                    ev.setDescription((String) evaluation.get("Element"));
                    ev.setDetails(JSONArrayUtil.jsonArrayToList((JSONArray) evaluation.get("Details")));
                    evaluationList.add(ev);
                }
            }
        }
        evaluationApprentissage.setElements(evaluationList);
        evaluationApprentissage.setTitre((String)obj.get("Titre"));
        evaluationApprentissage.setDetailPourcentage((String)obj.get("DetaillePourcentage"));
        evaluationApprentissage.setCriteres(JSONArrayUtil.jsonArrayToList((JSONArray)obj.get("Criteres")));
        evaluationApprentissage.setDetailLangue((String)obj.get("QualiteLangue"));
        cours.setEvaluationApprentissage(evaluationApprentissage);
        cours.setContribution((String)json.get("ContributionAuProgramme"));
        cours.setPresentation((String)json.get("Presentation"));
        cours.setObjectifIntegrateur((String)json.get("ObjectifIntegrateur"));
        cours.setManuelObligatoire((String)json.get("ManuelObligatoire"));
        cours.setObjectifApprentissage((String)json.get("ObjectifApprentissage"));
        List<Objectif> objectifList = new ArrayList<>();
        JSONArray objectifs = (JSONArray)json.get("Objectifs");
        if(objectifs != null) {
            for (Object o : objectifs) {
                if (o instanceof JSONObject) {
                    JSONObject objectif = (JSONObject) o;
                    Objectif ob = new Objectif();
                    ob.setCompetence((String) objectif.get("Objectif"));
                    ob.setElements(JSONArrayUtil.jsonArrayToList((JSONArray) objectif.get("Elements")));
                    objectifList.add(ob);
                }
            }
        }
        cours.setObjectifs(objectifList);
        return cours;
    }

    @Override
    public List<Cours> getCours() {
        return cache;
    }

    @Override
    public Cours getSingleCours(Long annee, String code) {
        Optional<Cours> cours = cache.stream()
                .filter(c -> c.getCode().equals(code))
                .filter(c -> c.getAnnee() == annee)
                .findFirst();
        if(cours.isEmpty())
            throw new NoSuchElementException("Il n'y a pas de plan cadre avec ce code de cours et cette annee");
        else
            return cours.get();
    }

    @Override
    public List<Cours> getAllRecentCours() {
        return cache.stream()
                .map(Cours::getCode)
                .distinct()
                .map(this::getRecentCours)
                .collect(Collectors.toList());
    }

    public Cours getRecentCours(String code){
        Optional<Cours> cours = cache.stream()
                .filter(c -> c.getCode().equals(code))
                .max(Comparator.comparing(Cours::getAnnee));
        if(cours.isEmpty())
            throw new NoSuchElementException("Il n'y a pas de cours avec ce code");
        else
            return cours.get();
    }

    @Scheduled(fixedRate = 20 * 1000L)
    public void UpdateCache(){
        List<Cours> cours = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(jsonPath+ "/cours"))) {
            List<String> result = walk.filter(Files::isRegularFile)
                    .map(Path::toString).collect(Collectors.toList());
            for(String s: result) {
                try (FileReader reader = new FileReader(s)) {
                    cours.add(jsonToCours((JSONObject)parser.parse(reader),new File(s)));
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
            this.cache = cours;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
