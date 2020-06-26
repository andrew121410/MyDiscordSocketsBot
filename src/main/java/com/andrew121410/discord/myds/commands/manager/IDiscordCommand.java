package com.andrew121410.discord.myds.commands.manager;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface IDiscordCommand {

    boolean onMessage(MessageReceivedEvent event, String[] args);
}
