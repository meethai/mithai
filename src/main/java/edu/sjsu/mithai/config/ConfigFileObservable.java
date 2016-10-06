package edu.sjsu.mithai.config;

import java.util.Observable;

public class ConfigFileObservable extends Observable {
    private static ConfigFileObservable ourInstance = new ConfigFileObservable();

    public static ConfigFileObservable getInstance() {
        return ourInstance;
    }

    private ConfigFileObservable() {
    }

    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }
}
