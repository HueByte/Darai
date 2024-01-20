package Darai.Services;

import com.fasterxml.jackson.core.JsonProcessingException;

import Darai.Commands.ChatCommand;
import Darai.Commands.PingCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandlerService extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        event.deferReply().queue();

        switch (event.getName()) {
            case "ping":
                PingCommand pingCommandInstance = new PingCommand();
                pingCommandInstance.ping(event);
                break;
            case "chat":
                ChatCommand chatCommandInstance = new ChatCommand();
                try {
                    chatCommandInstance.chat(event);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;
            case "reset":
                ChatCommand chatCommandService = new ChatCommand();
                chatCommandService.reset(event);
                break;
            default:
                event.reply("I can't handle that command right now").setEphemeral(true).queue();
        }
    }
}
