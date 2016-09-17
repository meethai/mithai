package edu.sjsu.mithai.sensors;

/**
 * Created by sjinturkar on 9/16/16.
 */

import java.util.Random;

public class FaultyTemperatureSensor extends AbstractDevice {

    private double min;
    private int variable;

    private Random random;

    public FaultyTemperatureSensor(String id) {
        super(id);
        random = new Random();
        min = random.nextDouble() * random.nextInt(1000);
        variable = Math.abs(random.nextInt(1000));
    }

    @Override
    public double sense() {
        return min + random.nextInt(variable);
    }
}
