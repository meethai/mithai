package edu.sjsu.mithai.sensors;


import edu.sjsu.mithai.data.TemperatureData;
import edu.sjsu.mithai.publisher.Publisher;

public abstract class AbstractDevice implements IDevice {

    protected String id;

    protected Publisher publisher = new Publisher();

    public AbstractDevice(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void sendData() {
        publisher.publish(new TemperatureData(id,Double.toString(sense()),"kelvin"));
    }
}
