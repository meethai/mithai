package edu.sjsu.mithai.export;

import edu.sjsu.mithai.export.http.HttpExporter;
import edu.sjsu.mithai.export.kafka.KafkaExporter;

/**
 * Created by sjinturkar on 9/18/16.
 */
public class ExporterFactory {

    public static IExporter getExporter(String type) {

        if (type == null) {
            return null;
        }

        switch (type) {
            case "KAFKA":
                return new KafkaExporter();
            case "HTTP":
                return new HttpExporter();

            default:
                return null;
        }
    }
}
