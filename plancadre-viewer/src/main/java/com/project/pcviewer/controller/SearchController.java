package com.project.pcviewer.controller;

import com.project.pcviewer.objects.Cours;
import com.project.pcviewer.objects.V1.SearchV1;
import com.project.pcviewer.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@CrossOrigin("*")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService){
        this.searchService = searchService;
    }

    @RequestMapping(path = "/{keyword}", method = RequestMethod.GET)
    public List<SearchV1> search(@PathVariable String keyword){
        return searchService.searchByKeyword(keyword);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public List<SearchV1> searchAll(){
        return searchService.getAll();
    }
}
