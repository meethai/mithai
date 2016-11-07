package edu.sjsu.mithai.export.http;

import edu.sjsu.mithai.export.ExportMessage;
import edu.sjsu.mithai.export.HttpExportMessage;
import edu.sjsu.mithai.export.IExporter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpExporter implements IExporter<ExportMessage> {

    private CloseableHttpClient client;

    public HttpExporter() {
    }

    @Override
    public void setup() throws Exception {
        this.client = HttpClients.createDefault();
    }

    @Override
    public void send(ExportMessage message) throws IOException {

        if (message instanceof HttpExportMessage) {
            HttpExportMessage message1 = (HttpExportMessage) message;
            System.out.println("Sending message over HTTP: " + message);
            System.out.println("URI:" + message1.getUri());
            HttpPost post = new HttpPost(message1.getUri());
            post.addHeader("content-type", "application/json");
            post.setEntity(new StringEntity(message1.getMessage()));
            CloseableHttpResponse response = client.execute(post);
            System.out.println(response.getStatusLine());
            EntityUtils.consume(response.getEntity());
        } else {
            System.out.println("HTTP message of type ExportMessage: " + message);
        }
    }

    @Override
    public void tearDown() throws IOException {
        client.close();
    }
}
