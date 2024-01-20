package Darai.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Darai.Models.ChatMessage;

public class MessageContainer {
    private List<ChatMessage> messageBag = new ArrayList<>();
    private List<ChatMessage> concurrentMessageBag = Collections.synchronizedList(messageBag);

    public void addMessage(String author, String message) {
        ChatMessage chatMessage = new ChatMessage(message, author);
        addMessage(chatMessage);
    }

    public void addMessage(ChatMessage chatMessage) {
        concurrentMessageBag.add(chatMessage);
    }

    public void overrideSession(List<ChatMessage> messages) {
        concurrentMessageBag.clear();
        concurrentMessageBag.addAll(messages);
    }

    public List<ChatMessage> getMessages() {
        return concurrentMessageBag;
    }

    public void clear() {
        concurrentMessageBag.clear();
    }

    public void removeMessage(int index) {
        concurrentMessageBag.remove(index);
    }

    public void removeMessages(int[] indexes) {
        for (int i = 0; i < indexes.length; i++) {
            concurrentMessageBag.remove(indexes[i]);
        }
    }
}
