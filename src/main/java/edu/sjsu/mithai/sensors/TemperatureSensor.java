package edu.sjsu.mithai.sensors;


import java.util.Random;

public class TemperatureSensor extends AbstractDevice {

    private double min;
    private Random random;

    public TemperatureSensor(String id) {
        super(id);
        random = new Random();
        min = random.nextDouble() + 100;
    }

    @Override
    public double sense() {
        return min + random.nextDouble();
    }

}
