package edu.sjsu.mithai.util;

public abstract class StoppableRunnableTask implements Runnable, Stoppable {

    protected boolean stop;

    public StoppableRunnableTask() {
        this.stop = false;
    }

    @Override
    public void stop() {
        stop = true;
    }

}
