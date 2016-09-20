package edu.sjsu.mithai.export;

/**
 * Created by sjinturkar on 9/18/16.
 */
public class Exporter {

    private IExporter exporter;

    public Exporter(String type) throws Exception {
        init(type);
    }

    private void init(String type) throws Exception {
        exporter = ExporterFactory.getExporter(type);
        exporter.setup();
        boolean shutDown = false;

        do {
            exporter.send();
            Thread.sleep(15000);
        } while (!shutDown);
        exporter.tearDown();
    }
}
