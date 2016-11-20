package edu.sjsu.mithai.data;

public class DataTuple {
    private String sensor;
    private Double value;

    public DataTuple(String sensor, Double value) {
        this.sensor = sensor;
        this.value = value;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
