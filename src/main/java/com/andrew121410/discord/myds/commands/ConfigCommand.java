package com.andrew121410.discord.myds.commands;

import com.andrew121410.discord.myds.Main;
import com.andrew121410.discord.myds.commands.manager.IDiscordCommand;
import com.andrew121410.discord.myds.config.GuildJacksonConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class ConfigCommand implements IDiscordCommand {

    private Main main;

    public ConfigCommand(Main main) {
        this.main = main;

        this.main.getCommandManager().registerCommand("config", this);
    }

    @Override
    public boolean onMessage(MessageReceivedEvent event, String[] args) {
        GuildJacksonConfig guildJacksonConfig = this.main.getMasterGuildManager().getConfig(event.getGuild().getId());

        if (args.length == 0) {
            String canEdits = "acceptingNewConnections"
                    + "\r\n " + "";

            String currentConfigFile = "GuildID = " + guildJacksonConfig.getGuildID()
                    + "\r\n " + "VerifiedMap = " + guildJacksonConfig.getVerifiedMap()
                    + "\r\n " + "IsAcceptingNewConnections = " + guildJacksonConfig.isAcceptingNewConnections();

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor("MyDiscordSockets")
                    .setTitle("Guild Configuration Menu")
                    .setDescription("Allows you to edit this Guild config.")
                    .addField("Allowed to edit.", canEdits, false)
                    .addField("Current Config File.", currentConfigFile, false)
                    .setFooter("Version: " + Main.VERSION + " | Developed by Andrew121410#2035")
                    .setColor(Color.YELLOW);

            event.getChannel().sendMessage(embedBuilder.build()).queue();
        }

        return true;
    }
}
