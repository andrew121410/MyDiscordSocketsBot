package com.andrew121410.discord.myds.commands;

import com.andrew121410.discord.myds.Main;
import com.andrew121410.discord.myds.commands.manager.IDiscordCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class HelpCommand implements IDiscordCommand {

    private Main main;

    public HelpCommand(Main main) {
        this.main = main;

        this.main.getCommandManager().registerCommand("help", this);
    }

    @Override
    public boolean onMessage(MessageReceivedEvent event, String[] args) {
        StringBuilder commandBuilder = new StringBuilder();
        this.main.getCommandManager().getCommandMap().forEach((k, v) -> {
            commandBuilder.append(this.main.getConfig().getPrefix());
            commandBuilder.append(k);
            commandBuilder.append("\r\n");
        });

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setAuthor("MyDiscordSockets")
                .setTitle("Help Menu")
                .setDescription("Shows help related to the bot.")
                .addField("Commands.", commandBuilder.toString(), false)
                .setFooter("Version: " + Main.VERSION + " | Developed by Andrew121410#2035")
                .setColor(Color.BLUE);

        event.getChannel().sendMessage(embedBuilder.build()).queue();
        return true;
    }
}
