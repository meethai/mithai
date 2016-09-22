package edu.sjsu.mithai.config;

public interface MithaiProperties {

    // Self node properties
    static String IP = "IP";
    static String ID = "ID";

    // INJECTOR PROPERTIES
    static String MQTT_BROKER = "MQTT_BROKER";
    static String MQTT_TOPIC = "MQTT_TOPIC";

    // EXPORTER PROPERTIES
    static String EXPORTER_TYPE = "EXPORTER_TYPE";
    static String EXPORTER_REMOTE_URI = "EXPORTER_REMOTE_IP";

}
