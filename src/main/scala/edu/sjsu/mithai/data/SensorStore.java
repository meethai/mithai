package edu.sjsu.mithai.data;

import edu.sjsu.mithai.sensors.IDevice;

import java.util.ArrayList;
import java.util.List;

public class SensorStore {

    private List<IDevice> devices;

    public SensorStore() {
        this.devices = new ArrayList<>();
    }

    public void addDevice(IDevice device) {
        devices.add(device);
    }

    public List<IDevice> getDevices() {
        return devices;
    }
}
