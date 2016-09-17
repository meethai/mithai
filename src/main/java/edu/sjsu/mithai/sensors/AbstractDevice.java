package edu.sjsu.mithai.sensors;


public abstract class AbstractDevice implements IDevice {

    protected String id;

    public AbstractDevice(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
