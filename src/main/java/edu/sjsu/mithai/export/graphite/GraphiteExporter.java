package edu.sjsu.mithai.export.graphite;

import com.codahale.metrics.graphite.Graphite;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.sjsu.mithai.export.ExportMessage;
import edu.sjsu.mithai.export.IExporter;

import java.io.IOException;

public class GraphiteExporter implements IExporter<ExportMessage> {

    private Graphite graphite;
    private String hostname;
    private int port;
    private JsonParser parser;

    public GraphiteExporter(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.parser = new JsonParser();
    }

    @Override
    public void setup() throws Exception {
        connectToGraphite();
    }

    @Override
    public void send(ExportMessage message) throws IOException {

        if (!graphite.isConnected()) {
            connectToGraphite();
        }

        JsonElement element = parser.parse(message.getMessage());
        if (element.isJsonObject()) {
            graphite.send(element.getAsJsonObject().get("key").getAsString(),
                    element.getAsJsonObject().get("value").getAsString(),
                    element.getAsJsonObject().get("time").getAsLong());
        }
    }

    @Override
    public void tearDown() throws IOException {
        graphite.close();
    }

    private void connectToGraphite() throws IOException {
        graphite = new Graphite(hostname, port);
//        graphite.connect();
    }
}
