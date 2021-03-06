package edu.sjsu.mithai.export;

import edu.sjsu.mithai.config.Configuration;

public class Exporter {

    private IExporter exporter;
    private Configuration configuration;

    public Exporter(Configuration configuration) throws Exception {
        this.configuration = configuration;
        this.exporter = ExporterFactory.getExporter(configuration);
    }

    public void setExporter(IExporter exporter) {
        this.exporter = exporter;
    }

    public IExporter getExporter() {
        return exporter;
    }
}
