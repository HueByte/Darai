package Darai.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ChatMessage
 */
public class ChatMessage {
    @JsonProperty("role")
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String role;

    @JsonProperty("message")
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public ChatMessage() {
    }

    public ChatMessage(String message, String role) {
        this.message = message;
        this.role = role;
    }
}