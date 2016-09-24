package edu.sjsu.mithai.sensors;


public abstract class AbstractDevice implements IDevice {

    protected String id;

//    protected Publisher publisher = new Publisher();

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
//        publisher.publish(new TemperatureData(id,Double.toString(sense()),"kelvin"));
    }
}
