package edu.sjsu.mithai.kafka.util;

import com.google.gson.Gson;

/**
 * Created by sushained on 9/17/16.
 */

public class JsonHelper {
    private Gson gson = new Gson();
    private static JsonHelper instance = new JsonHelper();

    public static JsonHelper getInstance() {
        return instance;
    }

    public String toJson(Object o) {
        return gson.toJson(o);
    }

    public Object fromJson(Class className, String json) {
        return gson.fromJson(json, className);
    }
}