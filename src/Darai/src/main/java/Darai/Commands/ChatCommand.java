package Darai.Commands;

import com.fasterxml.jackson.core.JsonProcessingException;

import Darai.Services.ChatService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ChatCommand {
    private ChatService _chatServiceInstance = new ChatService();

    public void chat(SlashCommandInteractionEvent event) throws JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        sb.append(">>> " + event.getUser().getAsMention() + "\n" + event.getOption("message").getAsString() + "\n\n");
        var messageFuture = _chatServiceInstance.exchangeAsync(event.getOption("message").getAsString());

        try {
            var chatMessage = messageFuture.get();
            sb.append(chatMessage.getMessage());
        } catch (Exception e) {
            sb.append("Error occurred: " + e.getMessage());
            System.err.println("Error occurred: " + e.getMessage());
        }

        event.getHook().sendMessage(sb.toString()).queue();
    }

    public void reset(SlashCommandInteractionEvent event) {
        _chatServiceInstance.reset();
        event.getHook().sendMessage("Done").queue();

    }
}
