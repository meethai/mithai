package edu.sjsu.mithai.apps.parkinglot;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.main.Mithai;

import java.util.HashMap;
import java.util.Map;

public class ParkingApplication extends Mithai {

    private Map<String, DummyParkingSensor> parkingSensorMap;

    public ParkingApplication() {
        this.parkingSensorMap = new HashMap<>();
    }

    public static void main(String[] args) {
        ParkingApplication app = new ParkingApplication();
        try {
            app.start(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected synchronized void loadDevices() {
        sensorStore.getDevices().clear();

        String localGraph = configuration.getProperty(MithaiProperties.LOCAL_GRAPH);
        JsonParser parser = new JsonParser();
        JsonElement parse = parser.parse(localGraph);

        int size = parse.getAsJsonArray().size();

        for (int i = 0; i< size; i++) {
            DummyParkingSensor sensor = new DummyParkingSensor(parse.getAsJsonArray().get(i).getAsJsonObject()
                    .get("deviceId").getAsString());
            parkingSensorMap.put(sensor.getId(), sensor);
            sensorStore.addDevice(sensor);
        }

    }
}
