package edu.sjsu.mithai.graphX;

import edu.sjsu.mithai.util.BaseTest;
import edu.sjsu.mithai.util.TaskManager;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Madhura on 9/26/16.
 */
public class GraphTaskTest extends BaseTest{

    public GraphTaskTest() throws IOException {
    }

    @Test
    @Override
    public void test() {
        TaskManager.getInstance().submitTask(new GraphTask());
        stopAfter(15);
    }
}