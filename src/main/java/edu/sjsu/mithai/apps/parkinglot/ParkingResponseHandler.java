package edu.sjsu.mithai.apps.parkinglot;

import com.google.gson.Gson;
import edu.sjsu.mithai.export.HttpExportMessage;
import edu.sjsu.mithai.spark.Store;
import edu.sjsu.mithai.util.Ihandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParkingResponseHandler implements Ihandler {

    private Gson gson;
    private Map<String, Integer> parkingStatus;

    public ParkingResponseHandler() {
        this.gson = new Gson();
        this.parkingStatus = new LinkedHashMap<>();
    }

    @Override
    public void handle(String functionName, String msg) {

        if (functionName.equals("ShortestPath")) {

            for (String key : ParkingApplication.parkingSensorMap.keySet()) {
                int status = ParkingApplication.parkingSensorMap.get(key).isParked() ? 1 : 0;
                parkingStatus.put(key, status);
            }
            System.out.println(functionName + "=>" + msg);

            HttpExportMessage message = new HttpExportMessage(gson.toJson(parkingStatus),
                    "http://localhost:3000/lots/allavailable");
            Store.httpMessageStore().addMessage(message);
        }
    }
}
