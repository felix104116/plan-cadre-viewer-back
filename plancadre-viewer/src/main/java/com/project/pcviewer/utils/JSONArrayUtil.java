package com.project.pcviewer.utils;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class JSONArrayUtil {
    @SuppressWarnings("unchecked")
    public static List<String> jsonArrayToList(JSONArray array){
        if(array != null)
            return new ArrayList<String>(array);
        else
            return new ArrayList<>();
    }
}
