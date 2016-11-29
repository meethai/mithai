package edu.sjsu.mithai.export;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.export.http.HttpExporter;
import edu.sjsu.mithai.spark.Store;
import edu.sjsu.mithai.util.StoppableRunnableTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpExporterTask extends StoppableRunnableTask {

    private final Logger logger = LoggerFactory.getLogger(ExporterTask.class);
    private static final String STOP_MESSAGE = "STOP";

    private Configuration configuration;
    private IExporter exporter;
    private MessageStore<HttpExportMessage> messageStore;

    public HttpExporterTask(Configuration configuration) throws Exception {
        this.configuration = configuration;
        this.messageStore = Store.httpMessageStore();
        exporter = new HttpExporter();
        exporter.setup();
    }

    @Override
    public void run() {

        while (true) {
            try {
                HttpExportMessage message = messageStore.getMessageQueue().take();

                if (message.getMessage().equals(STOP_MESSAGE)) {
                    break;
                }
                exporter.send(message);
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
            exporter.tearDown();
            messageStore.addMessage(new HttpExportMessage(STOP_MESSAGE, STOP_MESSAGE));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
