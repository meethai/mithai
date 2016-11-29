package edu.sjsu.mithai.export;

import edu.sjsu.mithai.util.BaseTest;
import edu.sjsu.mithai.util.TaskManager;
import org.junit.Test;

import java.io.IOException;

public class ExporterTaskTest extends BaseTest {

    public ExporterTaskTest() throws IOException {

    }

    @Test
    @Override
    public void test() throws Exception {

        MessageStore messageStore = new MessageStore(10);
        for (int i = 0; i < 10; i++) {
            messageStore.addMessage(new ExportMessage("Message" + i));
        }
        System.out.println(messageStore);
        TaskManager.getInstance().submitTask(new HttpExporterTask(config));

        stopAfter(50);
    }
}
