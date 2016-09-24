package edu.sjsu.mithai.util;

public abstract class StoppableRunnableTask implements Runnable, Executable, Stoppable {

    protected boolean stop;

    public StoppableRunnableTask() {
        this.stop = false;
    }

    @Override
    public void run() {
        do {
            execute();
        } while (!stop);
    }

    @Override
    public void stop() {
        stop = true;
    }

}
