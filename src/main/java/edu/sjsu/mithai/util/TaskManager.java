package edu.sjsu.mithai.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    private static TaskManager ourInstance = new TaskManager();

    private List<Stoppable> tasks;
    private ExecutorService threadPool;

    public static TaskManager getInstance() {
        return ourInstance;
    }

    private TaskManager() {
        this.threadPool = Executors.newCachedThreadPool();
        this.tasks = new ArrayList<>();
    }

    public void submitTask(StoppableRunnableTask task) {
        threadPool.submit(task);
        tasks.add(task);
    }

    public void stopAll() throws InterruptedException {

        for (Stoppable task : tasks) {
            task.stop();
        }

        threadPool.shutdown();
        threadPool.awaitTermination(60, TimeUnit.SECONDS);
    }

}
