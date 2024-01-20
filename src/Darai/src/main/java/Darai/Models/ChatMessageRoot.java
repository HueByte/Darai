package Darai.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessageRoot {
    @JsonProperty("messages")
    public List<ChatMessage> getMessages() {
        return this.messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    private List<ChatMessage> messages;
}