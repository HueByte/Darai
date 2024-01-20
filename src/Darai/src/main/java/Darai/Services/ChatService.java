package Darai.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import Darai.BotSingleton;
import Darai.Models.ChatMessage;
import Darai.Models.ChatMessageRoot;

public class ChatService {
    private MessageContainer _messageContainer;
    private ObjectMapper _objectMapper;

    public ChatService() {
        _messageContainer = BotSingleton.getInstance().get_messageContainer();
        _objectMapper = BotSingleton.getInstance().get_objectMapper();
    }

    public CompletableFuture<ChatMessage> exchangeAsync(String message) throws JsonProcessingException {
        ChatMessage chatMessage = new ChatMessage(message, "User");
        _messageContainer.addMessage(chatMessage);
        ChatMessageRoot chatMessageRoot = new ChatMessageRoot();
        chatMessageRoot.setMessages(_messageContainer.getMessages());

        var data = _objectMapper.writeValueAsString(chatMessageRoot);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:5000/api/Ai"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(data, StandardCharsets.UTF_8))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(responseBody -> {
                    try {
                        var bodyJson = responseBody.body();
                        List<ChatMessage> messages = _objectMapper.readValue(bodyJson, ChatMessageRoot.class)
                                .getMessages();

                        // for the prototype sake
                        _messageContainer.overrideSession(messages);

                        return messages.get(messages.size() - 1);
                    } catch (IOException e) {
                        throw new RuntimeException("Error parsing JSON response", e);
                    }
                });
    }

    public void reset() {
        _messageContainer.clear();
    }
}
