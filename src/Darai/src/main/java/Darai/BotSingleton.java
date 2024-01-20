package Darai;

import com.fasterxml.jackson.databind.ObjectMapper;

import Darai.Services.MessageContainer;

public final class BotSingleton {
    private static BotSingleton _instance;

    private MessageContainer _messageContainer = new MessageContainer();

    public MessageContainer get_messageContainer() {
        return _messageContainer;
    }

    private ObjectMapper _objectMapper = new ObjectMapper();

    public ObjectMapper get_objectMapper() {
        return _objectMapper;
    }

    private BotSingleton() {
    }

    public static BotSingleton getInstance() {
        if (_instance == null) {
            _instance = new BotSingleton();
        }
        return _instance;
    }
}
