package edu.sjsu.mithai.data;

import com.google.gson.Gson;

import java.util.Base64;

public class GenericSerializationHelper implements SerializationHelper<Object> {

    private Gson gson;
    private Class clazz;

    public GenericSerializationHelper(Class clazz) {
        this.clazz = clazz;
        this.gson = new Gson();
    }

    @Override
    public String serialize(Object data) throws Exception {
        return gson.toJson(data);
    }

    @Override
    public Object deserialize(String stream) throws Exception {
        String message = new String(Base64.getDecoder().decode(stream.getBytes()));
//        System.out.println(message);
        return gson.fromJson(message, clazz);
    }
}
