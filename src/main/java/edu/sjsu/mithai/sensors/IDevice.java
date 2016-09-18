package edu.sjsu.mithai.sensors;

public interface IDevice {

    public double sense();

    public String getId();

    public void sendData();
}
