package edu.sjsu.mithai.util;

public abstract class StoppableExecutableTask implements Runnable, Executable, Stoppable {

    protected boolean stop;

    public StoppableExecutableTask() {
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
