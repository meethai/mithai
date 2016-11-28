package edu.sjsu.mithai.apps.temperature;

import com.sun.org.apache.xpath.internal.Arg;
import edu.sjsu.mithai.sensors.IDevice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// TODO Access temperature sensors. Add them to sensor store. Generate data using data generation task.
public class RealTemperatureSensor implements IDevice {

    private static int RETRY_COUNT = 3;
    private static String COMMAND = "echo";
    private static String ARGS = "FAIL";


    public static void main(String[] args) throws IOException, InterruptedException {
        RealTemperatureSensor temperatureSensor = new RealTemperatureSensor();
        System.out.println(temperatureSensor.sense());
    }

    @Override
    public double sense() {

        int count = 0;

        while (count < RETRY_COUNT) {
            try {
                String value = readSensorValue();

                System.out.println(value);
                if (!value.equals("FAIL")) {
                    return Double.parseDouble(value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            count++;
        }

        return -1;
    }


    private String readSensorValue() throws Exception {
        String line;
        ProcessBuilder ps = new ProcessBuilder(COMMAND, ARGS);
        ps.redirectErrorStream(true);
        double val;
        Process pr = ps.start();

        assert pr != null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()))) {
            line = in.readLine();
            pr.waitFor();
            in.close();
        }
        return line;
    }

        @Override
        public String getId () {
            String ID;
            ID = "Sensor 1";
            return ID;
        }

        @Override
        public void sendData () {

        }
    }

