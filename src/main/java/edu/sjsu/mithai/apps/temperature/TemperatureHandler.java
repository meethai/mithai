package edu.sjsu.mithai.apps.temperature;

import com.google.gson.Gson;
import edu.sjsu.mithai.export.ExportMessage;
import edu.sjsu.mithai.spark.Store;
import edu.sjsu.mithai.util.Ihandler;

import java.util.HashMap;
import java.util.Map;

public class TemperatureHandler implements Ihandler {

    private Gson gson;
    public TemperatureHandler() {
        this.gson = new Gson();
    }

    @Override
    public void handle(String functionName, String msg) {

        if (functionName.equals("average")) {
            System.out.println("Average is =>" + msg);

            Map<String, String> data = new HashMap<>();
            data.put("key","temperature.average");
            data.put("value", msg);
            data.put("time", String.valueOf(System.currentTimeMillis()/1_000_000));

            ExportMessage exportMessage = new ExportMessage(gson.toJson(data));

            System.out.println("Data sending to Exporter: " + exportMessage.getMessage());
            Store.messageStore().addMessage(exportMessage);

        } else {
            System.out.println(functionName + "=>" + msg);
        }
    }
}
