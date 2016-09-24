package edu.sjsu.mithai.export.kafka;

import com.google.gson.Gson;
import edu.sjsu.mithai.export.IExporter;
import edu.sjsu.mithai.sensors.TemperatureSensor;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaExporter implements IExporter {
    private final String servers;
    private KafkaProducer<String, String> producer;
    private String topic;
    private Gson gson;

    public KafkaExporter(String topic, String servers) {
        this.topic = topic;
        this.servers = servers;
    }

    @Override
    public void setup() {
        Properties props = new Properties();
        props.put("bootstrap.servers", servers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("request.required.acks", "1");
        producer = new KafkaProducer<String, String>(props);
        gson = new Gson();
    }

    @Override
    public void send() {
        TemperatureSensor temperatureSensor = new TemperatureSensor("Sensor1");

        Double temperature = temperatureSensor.sense();

        Map<String, Double> map = new HashMap<>();
        map.put(temperatureSensor.getId(), temperature);

        String msg = gson.toJson(map);
        ProducerRecord<String, String> data = new ProducerRecord<String, String>(topic, "1", msg);
        Future<RecordMetadata> rs = producer.send(data,
                new Callback() {

                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception arg1) {
                        System.out.println("Record stored successfully!");
                    }
                });
        try {
            RecordMetadata rm = rs.get();
            msg = msg + " partition = " + rm.partition() + " offset ="
                    + rm.offset();
            System.out.println(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tearDown() {
        producer.close();
    }
}
