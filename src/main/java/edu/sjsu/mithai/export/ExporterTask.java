package edu.sjsu.mithai.export;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.util.StoppableRunnableTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ExporterTask extends StoppableRunnableTask {

    private final Logger logger = LoggerFactory.getLogger(ExporterTask.class);
    private static final String STOP_MESSAGE = "STOP";

    private Configuration configuration;
    private Exporter exporter;
    private long sendInterval;
    private MessageStore messageStore;

    public ExporterTask(Configuration configuration, MessageStore messageStore) throws Exception {
        this.configuration = configuration;
        this.sendInterval = Long.parseLong(configuration.getProperty(MithaiProperties.EXPORTER_TIME_INTERVAL));
        this.messageStore = messageStore;
        exporter = new Exporter(configuration);
        exporter.getExporter().setup();
    }

    @Override
    public void run() {

        //TODO Message added to store is sent immediately. If required add sleep to run task periodically.
        while (true) {
            try {
                ExportMessage message = messageStore.getMessageQueue().take();

                if (message.getMessage().equals(STOP_MESSAGE)) {
                    break;
                }
                exporter.getExporter().send(message);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
        try {
            exporter.getExporter().tearDown();
            messageStore.addMessage(new ExportMessage(STOP_MESSAGE));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
