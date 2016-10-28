package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.data.AvroGraphMetadata;
import edu.sjsu.mithai.data.AvroMetadataSerializationHelper;
import edu.sjsu.mithai.util.StoppableRunnableTask;
import org.apache.log4j.Logger;
import scala.reflect.ClassTag$;

import static edu.sjsu.mithai.config.MithaiProperties.MQTT_BROKER;

public class MQTTMetaDataRecieverTask extends StoppableRunnableTask {
    private static Logger logger = Logger.getLogger(MQTTDataReceiverTask.class);
    private MQTTReciever metadataReciever;
    private Configuration config;

    public MQTTMetaDataRecieverTask(Configuration config) {
        this.config = config;
    }

    @Override
    public void run() {
        try {
            logger.debug("Starting Mqtt Metadata Reciever...");
            metadataReciever = new MQTTReciever<AvroGraphMetadata>(config.getProperty(MQTT_BROKER), "metadata", ClassTag$.MODULE$.apply(AvroGraphMetadata.class));
            AvroMetadataSerializationHelper helper = new AvroMetadataSerializationHelper();
            metadataReciever.setSerializationHelper(helper);
            metadataReciever.start();
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        logger.debug("Mqtt Metadata Receiver stopping...");

        if (metadataReciever == null) {
            logger.error("Metadata Receiver is NULL");
            return;
        }
        metadataReciever.stop(true);
    }
}
