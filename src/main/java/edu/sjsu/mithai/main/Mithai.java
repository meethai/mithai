package edu.sjsu.mithai.main;

import edu.sjsu.mithai.config.ConfigMonitorTask;
import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.data.DataGenerationTask;
import edu.sjsu.mithai.data.SensorStore;
import edu.sjsu.mithai.mqtt.MQTTReceiverTask;
import edu.sjsu.mithai.sensors.TemperatureSensor;
import edu.sjsu.mithai.util.TaskManager;

import java.io.IOException;

import static edu.sjsu.mithai.config.MithaiProperties.NUMBER_OF_SENSORS;

public class Mithai {

    private static Configuration configuration;

    public static void main(String[] args) throws IOException, InterruptedException {

        //TODO file path will be provided by user
        configuration = new Configuration(Thread.currentThread().getContextClassLoader()
                .getResource("application.properties").getFile());

        SensorStore sensorStore = loadDevices();

        //Start tasks here
        TaskManager.getInstance().submitTask(new ConfigMonitorTask(configuration));

        TaskManager.getInstance().submitTask(new MQTTReceiverTask(configuration));

        TaskManager.getInstance().submitTask(new DataGenerationTask(configuration, sensorStore));

//        // Stop all tasks and wait 60 seconds to finish them
//        TaskManager.getInstance().stopAll();

        Runtime.getRuntime().addShutdownHook(new ShutDownHook());

    }

    private static SensorStore loadDevices() {
        SensorStore sensorStore = new SensorStore();

        for (int i = 0; i< Integer.parseInt(configuration.getProperty(NUMBER_OF_SENSORS)); i++) {
            sensorStore.addDevice(new TemperatureSensor("sensor-" + i));
        }

        return sensorStore;
    }

    static class ShutDownHook extends Thread {

        @Override
        public void run() {
            System.out.println("###Shutdown triggered.. Stopping all tasks..");
            try {
                TaskManager.getInstance().stopAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
