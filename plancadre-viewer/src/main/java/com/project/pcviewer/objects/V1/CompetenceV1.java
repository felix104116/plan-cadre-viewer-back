package com.project.pcviewer.objects.V1;

import java.util.List;

public class CompetenceV1 {
    private String id;
    private String enonce;
    private List<String> elements;

    public List<String> getElements() {
        return elements;
    }

    public void setElements(List<String> elements) {
        this.elements = elements;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnonce() {
        return enonce;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }
}
