package edu.sjsu.mithai.export;

import java.io.IOException;

/**
 * Created by sjinturkar on 9/18/16.
 */
public interface IExporter {

    public void setup() throws Exception;

    public void send() throws IOException;

    public void tearDown() throws IOException;

}
