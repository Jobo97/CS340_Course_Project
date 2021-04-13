package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net;

import com.google.gson.Gson;

public class JsonSerializer {

    public static String serialize(Object requestInfo) {
        return (new Gson()).toJson(requestInfo);
    }

    public static <T> T deserialize(String value, Class<T> returnType) {
        System.out.println(value);
        return (new Gson()).fromJson(value, returnType);
    }
}
