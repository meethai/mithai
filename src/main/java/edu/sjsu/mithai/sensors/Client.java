package edu.sjsu.mithai.sensors;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Client {

    public static final int TEMPERATURE_SENSORS = 3;
    public static final int FAULTY_TEMPERATURE_SENSORS = 0;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        List<IDevice> devices = new ArrayList<IDevice>();
        for (int i = 0; i < TEMPERATURE_SENSORS; i++) {
            devices.add(new TemperatureSensor("TemperatureSensor-" + i));
        }

        for (int i = 0; i < FAULTY_TEMPERATURE_SENSORS; i++) {
            devices.add(new FaultyTemperatureSensor("FaultyTemperatureSensor-" + i));
        }

        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < devices.size(); i++) {
                System.out.println(devices.get(i).getId() + ": " + devices.get(i).sense());
                devices.get(i).sendData();
            }
            Thread.sleep(1000);
        }
    }
}
