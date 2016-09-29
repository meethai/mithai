package edu.sjsu.mithai.graphX;


import edu.sjsu.mithai.util.StoppableRunnableTask;
import org.apache.spark.SparkConf;

public class GraphTask extends StoppableRunnableTask {

    GraphProc gp;

    SparkConf conf = new SparkConf()
            .setAppName("GraphCreator")
            .setMaster("local[2]");
    private boolean flag=true;

    public GraphTask() {
        // Define required variable
        gp = new GraphProc();

    }

    @Override
    public void run() {
        // Start running graphx
        while (flag)
            gp.gProcessor(conf);
    }

    @Override
    public void stop() {
        // stop your GraphX context
        flag=false;
    }


}
