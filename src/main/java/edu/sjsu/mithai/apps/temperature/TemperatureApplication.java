package edu.sjsu.mithai.apps.temperature;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.sjsu.mithai.apps.parkinglot.ParkingResponseHandler;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.main.Mithai;
import edu.sjsu.mithai.util.TaskManager;

import java.util.LinkedHashMap;
import java.util.Map;

public class TemperatureApplication extends Mithai {

    static Map<String, RealTemperatureSensor> temperatureSensorMap;

    public TemperatureApplication() {
        temperatureSensorMap = new LinkedHashMap<>();
    }

    public static void main(String[] args) {
        TemperatureApplication app = new TemperatureApplication();
        try {
            app.start(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected synchronized void loadDevices() {
        sensorStore.getDevices().clear();

        String localGraph = configuration.getProperty(MithaiProperties.LOCAL_GRAPH);
        JsonParser parser = new JsonParser();
        JsonElement parse = parser.parse(localGraph);

        int size = parse.getAsJsonArray().size();

        for (int i = 0; i< size; i++) {
            RealTemperatureSensor sensor = new RealTemperatureSensor(parse.getAsJsonArray().get(i).getAsJsonObject()
                    .get("deviceId").getAsString());
            temperatureSensorMap.put(sensor.getId(), sensor);
            sensorStore.addDevice(sensor);
        }
    }

    @Override
    protected synchronized void setupHandlers() {
        TaskManager.getInstance().addHandler(new ParkingResponseHandler());
    }
}
