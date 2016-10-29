package edu.sjsu.mithai.util;

import edu.sjsu.mithai.spark.SparkStreamingObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    private static TaskManager ourInstance = new TaskManager();

    private List<Stoppable> tasks;
    private ExecutorService threadPool;

    private TaskManager() {
        this.threadPool = Executors.newCachedThreadPool();
        this.tasks = new ArrayList<>();
    }

    public static TaskManager getInstance() {
        return ourInstance;
    }

    public synchronized void submitTask(StoppableExecutableTask task) {
        threadPool.submit(task);
        tasks.add(task);
    }

    public synchronized void submitTask(StoppableRunnableTask task) {
        threadPool.submit(task);
        tasks.add(task);
    }

    public synchronized void stopAll() throws InterruptedException {

        for (Stoppable task : tasks) {
            System.out.println("Stopping task:" + task);
            task.stop();
            System.out.println("Task stopped..");
        }

        // remove all tasks
        tasks.clear();

        threadPool.shutdown();
        System.out.println("Stopping streaming context..");
        SparkStreamingObject.streamingContext().stop(true, false);
        System.out.println("Streaming context stopped..");
        threadPool.awaitTermination(60, TimeUnit.SECONDS);
    }

    public synchronized void stop(Class clazz) {
        System.out.println(tasks);
        for(Stoppable task : tasks) {
            if (clazz.isInstance(task)) {
                task.stop();
            }
        }
    }
}
