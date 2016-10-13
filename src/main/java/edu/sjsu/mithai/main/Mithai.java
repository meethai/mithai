package edu.sjsu.mithai.main;

import edu.sjsu.mithai.config.ConfigFileObservable;
import edu.sjsu.mithai.config.ConfigMonitorTask;
import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.data.DataGenerationTask;
import edu.sjsu.mithai.data.MetadataGenerationTask;
import edu.sjsu.mithai.data.SensorStore;
import edu.sjsu.mithai.mqtt.MQTTDataReceiverTask;
import edu.sjsu.mithai.mqtt.MQTTMetaDataRecieverTask;
import edu.sjsu.mithai.sensors.TemperatureSensor;
import edu.sjsu.mithai.util.TaskManager;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import static edu.sjsu.mithai.config.MithaiProperties.NUMBER_OF_SENSORS;

public class Mithai implements Observer {

    private static Configuration configuration;
    private SensorStore sensorStore;

    public static void main(String[] args) throws IOException, InterruptedException, MqttException {
        Mithai mithai = new Mithai();
        mithai.start();
    }

    private void start() throws IOException, MqttException {
        ConfigFileObservable.getInstance().addObserver(this);

        //TODO file path will be provided by user
        configuration = new Configuration(Thread.currentThread().getContextClassLoader()
                .getResource("application.properties").getFile());

        sensorStore = new SensorStore();

         loadDevices();

        //Start tasks here
        TaskManager.getInstance().submitTask(new ConfigMonitorTask(configuration));

        TaskManager.getInstance().submitTask(new MQTTDataReceiverTask(configuration));

        TaskManager.getInstance().submitTask(new MQTTMetaDataRecieverTask(configuration));

        TaskManager.getInstance().submitTask(new DataGenerationTask(configuration, sensorStore));

        TaskManager.getInstance().submitTask(new MetadataGenerationTask(configuration));

       // SimpleMqttReceiver receiver = new SimpleMqttReceiver(configuration);


//        // Stop all tasks and wait 60 seconds to finish them
//        TaskManager.getInstance().stopAll();

        Runtime.getRuntime().addShutdownHook(new ShutDownHook());

    }

    private synchronized void loadDevices() {
        sensorStore.getDevices().clear();

        for (int i = 0; i< Integer.parseInt(configuration.getProperty(NUMBER_OF_SENSORS)); i++) {
            sensorStore.addDevice(new TemperatureSensor("sensor-" + i));
        }
    }

    @Override
    public void update(Observable observable, Object o) {

        if (observable instanceof ConfigFileObservable) {
            loadDevices();

            // Kick out old data generation task and start new one
            TaskManager.getInstance().stop(DataGenerationTask.class);

            try {
                TaskManager.getInstance().submitTask(new DataGenerationTask(configuration, sensorStore));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
