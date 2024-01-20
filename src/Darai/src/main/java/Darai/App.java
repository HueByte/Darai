package Darai;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.EnumSet;

import Darai.Services.CommandHandlerService;

public class App {
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");

        JDABuilder builder = JDABuilder
                .createDefault("temp",
                        EnumSet.noneOf(GatewayIntent.class));

        // Add command listener
        builder.addEventListeners(new CommandHandlerService());
        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.playing("Chaos"));

        var jda = builder.build();

        var commands = jda.updateCommands();

        commands.addCommands(
                Commands.slash("ping", "Ping pong the bot"),
                Commands.slash("chat", "Chat with the bot").addOption(OptionType.STRING, "message",
                        "Message to the bot"),
                Commands.slash("reset", "Reset the converstation with the bot"));

        commands.queue();
    }
}
