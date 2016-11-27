package edu.sjsu.mithai.export;

public class HttpExportMessage extends ExportMessage {

    private String uri;

    public HttpExportMessage(String message, String uri) {
        super(message);
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return "HttpExportMessage{" +
                "uri='" + uri + '\'' +
                "message=" + message + '\'' +
                '}';
    }
}
