package edu.sjsu.mithai.apps.parkinglot;

import edu.sjsu.mithai.sensors.IDevice;

import java.util.Random;

public class DummyParkingSensor implements IDevice {

    private boolean isParked;
    private String id;

    public DummyParkingSensor(String id) {
        this.id = id;
    }

    @Override
    public double sense() {

        Random random = new Random();

        isParked = random.nextBoolean();

        if (isParked) {
            return 1;
        }

        return 0;
    }

    @Override
    public String getId() {
        return id;
    }

    public boolean isParked() {
        return isParked;
    }

    public void setParked(boolean parked) {
        isParked = parked;
    }
}
