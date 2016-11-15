package edu.sjsu.mithai.export;

import java.io.IOException;

public interface IExporter<T> {

    void setup() throws Exception;

    void send(T message) throws IOException;

    void tearDown() throws IOException;

}
