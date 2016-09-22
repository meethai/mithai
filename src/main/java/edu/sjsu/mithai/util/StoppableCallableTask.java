package edu.sjsu.mithai.util;

import java.util.concurrent.Callable;

public abstract class StoppableCallableTask<T> implements Callable<T>, Executable, Stoppable {

    protected boolean stop;

    @Override
    public void execute() {

    }

    @Override
    public void stop() {
        stop = true;
    }

}
