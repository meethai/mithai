package edu.sjsu.mithai.export;

import java.io.IOException;

public interface IExporter {

    public void setup() throws Exception;

    public void send(ExportMessage message) throws IOException;

    public void tearDown() throws IOException;

}
