package Darai.Commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingCommand {
    public void ping(SlashCommandInteractionEvent event) {
        event.getHook().sendMessage("Pong!").queue();
    }
}
