package edu.sjsu.mithai.export.http;

import edu.sjsu.mithai.export.ExportMessage;
import edu.sjsu.mithai.export.IExporter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpExporter implements IExporter {

    private CloseableHttpClient client;
    private String uri;

    public HttpExporter(String uri) {
        this.uri = uri;
    }

    @Override
    public void setup() throws Exception {
        this.client = HttpClients.createDefault();
    }

    @Override
    public void send(ExportMessage message) throws IOException {
//        System.out.println("Sending message: " + message);
        HttpPost post = new HttpPost(uri);
        post.addHeader("content-type", "application/json");
        post.setEntity(new StringEntity(message.getMessage()));
        CloseableHttpResponse response = client.execute(post);

        EntityUtils.consume(response.getEntity());
//        System.out.println(response.getStatusLine());
    }

    @Override
    public void tearDown() throws IOException {
        client.close();
    }
}
