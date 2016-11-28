package edu.sjsu.mithai.apps.parkinglot;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.main.Mithai;
import edu.sjsu.mithai.util.TaskManager;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParkingApplication extends Mithai {

    static Map<String, DummyParkingSensor> parkingSensorMap;

    public ParkingApplication() {
        parkingSensorMap = new LinkedHashMap<>();
    }

    public static void main(String[] args) {
        ParkingApplication app = new ParkingApplication();
        try {
            app.start(args[0]);
            TaskManager.getInstance().submitTask(new ParkingClient());
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
