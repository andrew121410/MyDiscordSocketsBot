package com.andrew121410.discord.myds.utils;

import com.andrew121410.discord.myds.Main;
import com.andrew121410.discord.myds.config.ConfigUtils;
import com.andrew121410.discord.myds.config.GuildJacksonConfig;
import net.dv8tion.jda.api.entities.Guild;

public class GuildInitializer {

    private Main main;

    public GuildInitializer(Main main) {
        this.main = main;
    }

    public void join(Guild guild) {
        GuildJacksonConfig guildJacksonConfig = ConfigUtils.loadGuildConfig(guild.getId());
        this.main.getMasterGuildManager().getGuildsConfigMap().putIfAbsent(guild.getId(), guildJacksonConfig);
    }

    public void leave(Guild guild) {
        this.main.getMasterGuildManager().getGuildsConfigMap().remove(guild.getId());
        ConfigUtils.deleteGuildConfig(guild.getId());
    }
}
