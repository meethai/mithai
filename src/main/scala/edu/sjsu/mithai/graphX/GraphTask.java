package edu.sjsu.mithai.graphX;


import edu.sjsu.mithai.util.StoppableExecutableTask;
import org.apache.spark.SparkConf;

public class GraphTask extends StoppableExecutableTask {

    GraphProc gp;

    SparkConf conf = new SparkConf()
            .setAppName("GraphCreator")
            .setMaster("local[2]");

    public GraphTask() {
        // Define required variable
        gp = new GraphProc();

    }

    @Override
    public void execute() {
        System.out.println("Hello world!");
        gp.gProcessor(conf);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
