package edu.sjsu.mithai.graphX;

import edu.sjsu.mithai.util.TaskManager;
import org.junit.Test;

/**
 * Created by Madhura on 9/26/16.
 */
public class GraphTaskTest {

    @Test
    public void testRun() {

        TaskManager.getInstance().submitTask(new GraphTask());
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            TaskManager.getInstance().stopAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}