package com.andrew121410.discord.myds.events;

import com.andrew121410.discord.myds.Main;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

public class DiscordEvents {

    private Main main;

    public DiscordEvents(Main main) {
        this.main = main;
    }

    @SubscribeEvent
    public void onGuildJoinEvent(GuildJoinEvent event){
        this.main.getGuildInitializer().join(event.getGuild());
    }

    @SubscribeEvent
    public void onGuildLeaveEvent(GuildLeaveEvent event){
        this.main.getGuildInitializer().leave(event.getGuild());
    }
}
