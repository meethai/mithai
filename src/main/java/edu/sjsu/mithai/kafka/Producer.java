package edu.sjsu.mithai.kafka;

/**
 * Created by sushained on 9/17/16.
 */

import java.util.*;
import java.util.concurrent.Future;

import edu.sjsu.mithai.kafka.util.JsonHelper;
import edu.sjsu.mithai.sensors.TemperatureSensor;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class Producer {

    private static KafkaProducer<String, String> producer;
    private static String topic = "temp";

    public Producer() {

    }

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "52.42.54.243:9092");
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("request.required.acks", "1");
        producer = new KafkaProducer<String, String>(props);

        TemperatureSensor temperatureSensor = new TemperatureSensor("Sensor1");

        Double temperature = temperatureSensor.sense();

        Map<String, Double> map = new HashMap<>();
        map.put(temperatureSensor.getId(), temperature);

        String msg = JsonHelper.getInstance().toJson(map);
        ProducerRecord<String, String> data = new ProducerRecord<String, String>(
                topic, "1", msg);
        Future<RecordMetadata> rs = producer.send(data,

                new Callback() {

                    @Override
                    public void onCompletion(RecordMetadata recordMetadata,
                                             Exception arg1) {
                        System.out.println("Record stored successfully!");
                    }
                });

        try {
            RecordMetadata rm = rs.get();
            msg = msg + " partition = " + rm.partition() + " offset ="
                    + rm.offset();
            System.out.println(msg);
        } catch (Exception e) {
            System.out.println(e);
        }
        producer.close();
    }
}
