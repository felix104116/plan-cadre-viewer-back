package com.project.pcviewer.objects;

import java.util.List;

public class ElementCompetence {
    private Long id;
    private String description;
    private List<String> criteres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCriteres() {
        return criteres;
    }

    public void setCriteres(List<String> criteres) {
        this.criteres = criteres;
    }
}
