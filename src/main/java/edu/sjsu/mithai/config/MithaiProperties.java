package edu.sjsu.mithai.config;

public interface MithaiProperties {

    // Self node properties
    String IP = "IP";
    String ID = "ID";
    String CONNECTED_DEVICE_IDS = "CONNECTED_DEVICE_IDS";
    String LOCAL_GRAPH = "LOCAL_GRAPH";

    // Sensor & Data generation related properties
    String NUMBER_OF_SENSORS = "NUMBER_OF_SENSORS";
    String DATA_GENERATION_INTERVAL = "DATA_GENERATION_INTERVAL";

    // INJECTOR PROPERTIES
    String MQTT_BROKER = "MQTT_BROKER";
    String MQTT_TOPIC = "MQTT_TOPIC";

    // Visualization System Properties
    String VISUALIZATION_SYSTEM_EXPORTER_URL = "VISUALIZATION_SYSTEM_EXPORTER_URL";

    // EXPORTER PROPERTIES
    String EXPORTER_TYPE = "EXPORTER_TYPE";
    String EXPORTER_REMOTE_URI = "EXPORTER_REMOTE_IP";
    String EXPORTER_KAFKA_TOPIC = "EXPORTER_KAFKA_TOPIC";
    String EXPORTER_TIME_INTERVAL = "EXPORTER_TIME_INTERVAL";

    //Graph Processor Tasks
    String ENTRY = "ENTRY";
    String TASK_LIST = "TASK_LIST";
}
