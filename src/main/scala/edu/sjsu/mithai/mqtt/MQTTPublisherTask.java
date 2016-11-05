package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.export.ExporterTask;
import edu.sjsu.mithai.export.MessageStore;
import edu.sjsu.mithai.spark.Store;
import edu.sjsu.mithai.util.StoppableRunnableTask;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

public class MQTTPublisherTask extends StoppableRunnableTask {

    private static final String STOP_MESSAGE = "STOP";
    private final Logger logger = LoggerFactory.getLogger(ExporterTask.class);
    private Configuration configuration;
    private MessageStore<Tuple2<String, String>> messageStore;
    private MQTTPublisher publisher;

    public MQTTPublisherTask(Configuration configuration) {
        this.configuration = configuration;
        this.messageStore = Store.mqttMessageStore();
        this.publisher = MqttService.getPublisher(configuration);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Tuple2<String, String> tuple = messageStore.getMessageQueue().take();

                if (tuple._2().equals(STOP_MESSAGE)) {
                    break;
                }

                if (!publisher.client().isConnected()) {
                    publisher.client().connect();
                }

                publisher.publishData(tuple._2(), tuple._1());
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            } catch (MqttSecurityException e) {
                e.printStackTrace();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
        try {
            publisher.client().disconnect(1000);
            messageStore.addMessage(new Tuple2<>(STOP_MESSAGE, STOP_MESSAGE));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
