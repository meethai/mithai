package edu.sjsu.mithai.export;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.export.console.ConsoleExporter;
import edu.sjsu.mithai.export.graphite.GraphiteExporter;
import edu.sjsu.mithai.export.http.HttpExporter;
import edu.sjsu.mithai.export.kafka.KafkaExporter;

import static edu.sjsu.mithai.config.MithaiProperties.*;

public class ExporterFactory {

    public static IExporter getExporter(Configuration configuration) {
        String type = configuration.getProperty(MithaiProperties.EXPORTER_TYPE);

        if (type == null) {
            return null;
        }

        switch (type) {
            case "KAFKA":
                return new KafkaExporter(configuration.getProperty(EXPORTER_KAFKA_TOPIC),
                        configuration.getProperty(EXPORTER_REMOTE_URI));

            case "HTTP":
                return new HttpExporter();

            case "CONSOLE":
                return new ConsoleExporter();

            case "GRAPHITE":
                return new GraphiteExporter(configuration.getProperty(GRAPHITE_HOSTNAME),
                        Integer.parseInt(configuration.getProperty(GRAPHITE_PORT)));

            default:
                return null;
        }
    }
}
