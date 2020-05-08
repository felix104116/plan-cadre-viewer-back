package com.project.pcviewer.objects;

import com.project.pcviewer.objects.V1.CompetenceV1;

import java.util.List;

public class Cours {
    private String id;
    private String titre;
    private String code;
    private String presentation;
    private String contribution;
    private String objectifIntegrateur;
    private String manuelObligatoire;
    private String objectifApprentissage;
    private EvaluationApprentissage evaluationApprentissage;
    private List<Objectif> objectifs;
    private List<CompetenceV1> competences;
    private List<String> recommandations;
    private List<AutreCours> prealables;
    private List<AutreCours> successeurs;

    private Programme programme;
    private int annee;
    private int session;
    private int nbHeureTheorie;
    private int nbHeurePratique;
    private int nbHeurePersonnel;

    public List<Objectif> getObjectifs() {
        return objectifs;
    }

    public void setObjectifs(List<Objectif> objectifs) {
        this.objectifs = objectifs;
    }

    public EvaluationApprentissage getEvaluationApprentissage() {
        return evaluationApprentissage;
    }

    public void setEvaluationApprentissage(EvaluationApprentissage evaluationApprentissage) {
        this.evaluationApprentissage = evaluationApprentissage;
    }

    public List<CompetenceV1> getCompetences() {
        return competences;
    }

    public void setCompetences(List<CompetenceV1> competences) {
        this.competences = competences;
    }

    public String getObjectifApprentissage() {
        return objectifApprentissage;
    }

    public void setObjectifApprentissage(String objectifApprentissage) {
        this.objectifApprentissage = objectifApprentissage;
    }

    public List<AutreCours> getPrealables() {
        return prealables;
    }

    public void setPrealables(List<AutreCours> prealables) {
        this.prealables = prealables;
    }

    public List<AutreCours> getSuccesseurs() {
        return successeurs;
    }

    public void setSuccesseurs(List<AutreCours> successeurs) {
        this.successeurs = successeurs;
    }

    public List<String> getRecommandations() {
        return recommandations;
    }

    public void setRecommandations(List<String> recommandations) {
        this.recommandations = recommandations;
    }

    public String getManuelObligatoire() {
        return manuelObligatoire;
    }

    public void setManuelObligatoire(String manuelObligatoire) {
        this.manuelObligatoire = manuelObligatoire;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public int getNbHeureTheorie() {
        return nbHeureTheorie;
    }

    public void setNbHeureTheorie(int nbHeureTheorie) {
        this.nbHeureTheorie = nbHeureTheorie;
    }

    public int getNbHeurePratique() {
        return nbHeurePratique;
    }

    public void setNbHeurePratique(int nbHeurePratique) {
        this.nbHeurePratique = nbHeurePratique;
    }

    public int getNbHeurePersonnel() {
        return nbHeurePersonnel;
    }

    public void setNbHeurePersonnel(int nbHeurePersonnel) {
        this.nbHeurePersonnel = nbHeurePersonnel;
    }

    public String getObjectifIntegrateur() {
        return objectifIntegrateur;
    }

    public void setObjectifIntegrateur(String objectifIntegrateur) {
        this.objectifIntegrateur = objectifIntegrateur;
    }
}
