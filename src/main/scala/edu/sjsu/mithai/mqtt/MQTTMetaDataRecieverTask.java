package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.data.AvroGraphMetadata;
import edu.sjsu.mithai.data.AvroMetadataSerializationHelper;
import edu.sjsu.mithai.util.StoppableRunnableTask;
import org.apache.log4j.Logger;
import scala.reflect.ClassTag$;

import static edu.sjsu.mithai.config.MithaiProperties.MQTT_BROKER;

/**
 * Created by kaustubh on 10/12/16.
 */
public class MQTTMetaDataRecieverTask extends StoppableRunnableTask {
    private static Logger logger = Logger.getLogger(MQTTDataReceiverTask.class);
    private MQTTReciever metadataReciever;
    private Configuration config;

    public MQTTMetaDataRecieverTask(Configuration config) {
        this.config = config;
    }

    @Override
    public void run() {
        logger.debug("Mqtt metadataReciever running....");
        metadataReciever = new MQTTReciever<AvroGraphMetadata>(config.getProperty(MQTT_BROKER), "metadata", ClassTag$.MODULE$.apply(AvroGraphMetadata.class));
        AvroMetadataSerializationHelper helper = new AvroMetadataSerializationHelper();
//        AvroSerializationHelper av = new AvroSerializationHelper();
//        try {
//            av.loadSchema("metadata.json");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        metadataReciever.setSerializationHelper(helper);
        metadataReciever.start();
    }

    @Override
    public void stop() {
        logger.debug("stopping...");
        metadataReciever.stop(true);
    }

}
