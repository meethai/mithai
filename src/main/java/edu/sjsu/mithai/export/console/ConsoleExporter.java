package edu.sjsu.mithai.export.console;

import edu.sjsu.mithai.export.ExportMessage;
import edu.sjsu.mithai.export.IExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ConsoleExporter implements IExporter {

    final Logger logger = LoggerFactory.getLogger(ConsoleExporter.class);

    @Override
    public void setup() throws Exception {

    }

    @Override
    public void send(ExportMessage message) throws IOException {
        logger.info(this.getClass().getName()+" Sending message: {}", message);
    }

    @Override
    public void tearDown() throws IOException {

    }
}
