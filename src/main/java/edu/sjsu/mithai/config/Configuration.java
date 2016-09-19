package edu.sjsu.mithai.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    protected String propertyFile;
    protected Properties properties;

    public Configuration(String propertyFile) throws IOException {
        this.propertyFile = propertyFile;
        this.properties = new Properties();
        properties.load(new FileReader(propertyFile));
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setPropertyFile(String propertyFile) {
        this.propertyFile = propertyFile;
    }

    public String getPropertyFile() {
        return propertyFile;
    }
}

