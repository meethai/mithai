package edu.sjsu.mithai.export;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageStore<T> {

    private LinkedBlockingQueue<T> messageQueue;
    private int size;

    public MessageStore(int size) {
        this.size = size;
        this.messageQueue = new LinkedBlockingQueue<>();
    }

    public void addMessage(T message) {

        if (messageQueue.size() < size) {
            messageQueue.add(message);
        }
    }

    public void addMessages(List<T> messages) {
        messageQueue.addAll(messages);
    }

    public LinkedBlockingQueue<T> getMessageQueue() {
        return messageQueue;
    }

    @Override
    public String toString() {
        return "MessageStore{" +
                "messageQueue=" + messageQueue +
                '}';
    }
}
