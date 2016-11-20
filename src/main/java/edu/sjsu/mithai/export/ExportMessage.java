package edu.sjsu.mithai.export;

import java.io.Serializable;

public class ExportMessage implements Serializable {

    //TODO in future we may need to add fields to this.

    protected final String message;

    public  ExportMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ExportMessage{" +
                "message:" + message +
                '}';
    }
}
