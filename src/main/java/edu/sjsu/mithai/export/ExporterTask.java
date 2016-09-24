package edu.sjsu.mithai.export;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.util.StoppableExecutableTask;

import java.io.IOException;

public class ExporterTask extends StoppableExecutableTask {

    private Configuration configuration;
    private Exporter exporter;
    private long sendInterval;

    public ExporterTask(Configuration configuration) throws Exception {
        this.configuration = configuration;
        this.sendInterval = Long.parseLong(configuration.getProperty(MithaiProperties.EXPORTER_TIME_INTERVAL));
        exporter = new Exporter(configuration);
        exporter.getExporter().setup();
    }

    @Override
    public void execute() {
        try {
            exporter.getExporter().send();
            Thread.sleep(sendInterval);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        super.stop();
        try {
            exporter.getExporter().tearDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
