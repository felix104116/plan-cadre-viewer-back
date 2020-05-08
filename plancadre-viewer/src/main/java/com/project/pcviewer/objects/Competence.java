package com.project.pcviewer.objects;

import java.util.List;

public class Competence {
    private String codeCompetence;
    private String enonce;
    private List<String> contextes;
    private List<ElementCompetence> elements;

    public String getEnonce() {
        return enonce;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public String getCodeCompetence() {
        return codeCompetence;
    }

    public void setCodeCompetence(String codeCompetence) {
        this.codeCompetence = codeCompetence;
    }

    public List<String> getContextes() {
        return contextes;
    }

    public void setContextes(List<String> contextes) {
        this.contextes = contextes;
    }

    public List<ElementCompetence> getElements() {
        return elements;
    }

    public void setElements(List<ElementCompetence> elements) {
        this.elements = elements;
    }
}
