package edu.sjsu.mithai.export.kafka;

import edu.sjsu.mithai.export.ExportMessage;
import edu.sjsu.mithai.export.IExporter;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaExporter implements IExporter<ExportMessage> {
    private final String servers;
    private KafkaProducer<String, String> producer;
    private String topic;

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
    }

    @Override
    public void send(ExportMessage message) {

        ProducerRecord<String, String> data = new ProducerRecord<String, String>(topic, "1", message.getMessage());
        Future<RecordMetadata> rs = producer.send(data,
                new Callback() {

                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception arg1) {
                        System.out.println("Record stored successfully!");
                    }
                });
        try {
            RecordMetadata rm = rs.get();
            String msg = message.getMessage() + " partition = " + rm.partition() + " offset ="
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
