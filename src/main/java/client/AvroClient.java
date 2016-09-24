package client;

import org.apache.avro.Schema;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AvroClient {

    public static void main(String[] args) throws IOException {
        AvroClient c = new AvroClient();
        c.test();
    }

    private void test() throws IOException {
        Schema.Parser parser = new Schema.Parser();
        URL url = getClass().getClassLoader().getResource("user.avsc.json");
        System.out.println(url);
        Schema parse = parser.parse(new File(url.getFile()));
        System.out.println(parse.getFields());
    }
}

