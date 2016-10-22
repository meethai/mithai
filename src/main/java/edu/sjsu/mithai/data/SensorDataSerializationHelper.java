package edu.sjsu.mithai.data;

import com.google.gson.Gson;

import java.util.Base64;

public class SensorDataSerializationHelper implements SerializationHelper<SensorData> {

    private Gson gson;

    public SensorDataSerializationHelper() {
        this.gson = new Gson();
    }

    @Override
    public String serialize(SensorData data) throws Exception {
        String sdata = gson.toJson(data);
        System.out.println(sdata);
        return Base64.getEncoder().encodeToString(sdata.getBytes());
    }

    @Override
    public SensorData deserialize(String stream) throws Exception {
        String message = new String(Base64.getDecoder().decode(stream.getBytes()));
        SensorData data =  gson.fromJson(message, SensorData.class);
        System.out.println(data);
        return data;
    }
}
