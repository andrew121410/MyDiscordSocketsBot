package com.andrew121410.discord.myds.manager;

import com.andrew121410.discord.myds.Main;
import com.andrew121410.discord.myds.config.ConfigUtils;
import com.andrew121410.discord.myds.config.GuildJacksonConfig;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MasterGuildManager {

    @Getter
    private Map<String, GuildJacksonConfig> guildsConfigMap;

    private Main main;

    public MasterGuildManager(Main main) {
        this.main = main;
        this.guildsConfigMap = new HashMap<>();

        this.loadAllGuildsFromConfig();
    }

    public void loadAllGuildsFromConfig() {
        File guildConfigFolder = new File("guilds-configs");
        if (!guildConfigFolder.exists()) {
            guildConfigFolder.mkdir();
            System.out.println("loadAllGuildsFromConfig() : FAILED -> No need to load nothing.");
            return;
        }

        final long startTime = System.nanoTime();
        List<File> files = Arrays.stream(guildConfigFolder.listFiles()).filter(file -> file.getName().endsWith(".yml")).collect(Collectors.toList());
        files.forEach(file -> {
            GuildJacksonConfig guildJacksonConfig = ConfigUtils.loadGuildConfig(file);
            this.guildsConfigMap.put(guildJacksonConfig.getGuildID(), guildJacksonConfig);
        });
        final long duration = System.nanoTime() - startTime;
        long ms = TimeUnit.NANOSECONDS.toMillis(startTime - duration);
        System.out.println("loadAllGuildsFromConfig() -> Time: " + ms + " Ms");
    }

    public GuildJacksonConfig getConfig(String id) {
        Guild guild = this.main.getJda().getGuildById(id);
        if (this.getGuildsConfigMap().get(id) == null) {
            this.main.getGuildInitializer().join(guild);
        }
        return this.getGuildsConfigMap().get(id);
    }

    public void saveConfig(String id) {
        GuildJacksonConfig guildJacksonConfig = getConfig(id);
        ConfigUtils.deleteGuildConfig(id);
        ConfigUtils.saveGuildConfig(guildJacksonConfig);
    }

    public void saveAllConfigs() {
        this.getGuildsConfigMap().forEach((k, v) -> saveConfig(k));
    }
}
