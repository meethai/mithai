package edu.sjsu.mithai.export;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageStore {

    private LinkedBlockingQueue<ExportMessage> messageQueue;

    public MessageStore() {
        this.messageQueue = new LinkedBlockingQueue<>();
    }

    public void addMessage(ExportMessage message) {

        if (messageQueue.size() < 10) {
            messageQueue.add(message);
        }
    }

    public void addMessages(List<ExportMessage> messages) {
        messageQueue.addAll(messages);
    }

    public LinkedBlockingQueue<ExportMessage> getMessageQueue() {
        return messageQueue;
    }

    @Override
    public String toString() {
        return "MessageStore{" +
                "messageQueue=" + messageQueue +
                '}';
    }
}
