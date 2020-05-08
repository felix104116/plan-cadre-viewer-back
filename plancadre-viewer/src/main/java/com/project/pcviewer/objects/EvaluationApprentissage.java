package com.project.pcviewer.objects;

import java.util.List;

public class EvaluationApprentissage {
    private String description;
    private String titre;
    private List<Evaluation> elements;
    private List<String> criteres;
    private String detailPourcentage;
    private String detailLangue;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDetailLangue() {
        return detailLangue;
    }

    public void setDetailLangue(String detailLangue) {
        this.detailLangue = detailLangue;
    }

    public String getDetailPourcentage() {
        return detailPourcentage;
    }

    public void setDetailPourcentage(String detailPourcentage) {
        this.detailPourcentage = detailPourcentage;
    }

    public List<String> getCriteres() {
        return criteres;
    }

    public void setCriteres(List<String> criteres) {
        this.criteres = criteres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Evaluation> getElements() {
        return elements;
    }

    public void setElements(List<Evaluation> elements) {
        this.elements = elements;
    }
}
