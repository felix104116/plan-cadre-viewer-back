package com.project.pcviewer.service;

import com.project.pcviewer.objects.V1.SearchV1;

import java.util.List;

public interface SearchService {
    List<SearchV1> searchByKeyword(String keyword);
    List<SearchV1> getAll();
}
