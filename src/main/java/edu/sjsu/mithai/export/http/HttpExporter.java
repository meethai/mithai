package edu.sjsu.mithai.export.http;

import com.google.gson.Gson;
import edu.sjsu.mithai.export.IExporter;
import edu.sjsu.mithai.sensors.TemperatureSensor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpExporter implements IExporter {

    private CloseableHttpClient client;
    private Gson gson;

    @Override
    public void setup() throws Exception {
        this.client = HttpClients.createDefault();
        this.gson = new Gson();
    }

    @Override
    public void send() throws IOException {
        TemperatureSensor temperatureSensor = new TemperatureSensor("Sensor1");
        Double temperature = temperatureSensor.sense();
        Map<String, Double> map = new HashMap<>();
        map.put(temperatureSensor.getId(), temperature);
        String msg = gson.toJson(map);

        System.out.println("Sending message: " + msg);
        HttpPost post = new HttpPost("https://httpbin.org/post");
        post.setEntity(new ByteArrayEntity(msg.getBytes()));
        CloseableHttpResponse response = client.execute(post);

        System.out.println(response.getStatusLine());

    }

    @Override
    public void tearDown() throws IOException {
        client.close();
    }
}
