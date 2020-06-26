package com.andrew121410.discord.myds.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;

import java.io.File;

public class ConfigUtils {

    @SneakyThrows
    public static DiscordJacksonConfig loadMainConfig() {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        File configFolder = new File("config");
        File configFile = new File(configFolder, "config.yml");
        if (!configFolder.exists()) {
            configFolder.mkdir();
            objectMapper.writeValue(configFile, new DiscordJacksonConfig());
            return null;
        }
        return objectMapper.readValue(configFile, DiscordJacksonConfig.class);
    }

    @SneakyThrows
    public static GuildJacksonConfig loadGuildConfig(String id) {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        File guildFolder = new File("guilds-configs");
        File guildConfig = new File(guildFolder, id + ".yml");
        if (!guildFolder.exists()) {
            guildFolder.mkdir();
        }
        if (guildConfig.exists()) {
            return objectMapper.readValue(guildConfig, GuildJacksonConfig.class);
        }
        return loadGuildConfig(guildConfig);
    }

    @SneakyThrows
    public static GuildJacksonConfig loadGuildConfig(File file) {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(file, GuildJacksonConfig.class);
    }

    public static boolean deleteGuildConfig(String id) {
        File guildFolder = new File("guilds-configs");
        if (!guildFolder.exists()) {
            guildFolder.mkdir();
            return false;
        }
        File guildConfig = new File(guildFolder, id + ".yml");
        return guildConfig.delete();
    }

    @SneakyThrows
    public static void saveGuildConfig(GuildJacksonConfig guildJacksonConfig) {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        File guildFolder = new File("guilds-configs");
        if (!guildFolder.exists()) {
            guildFolder.mkdir();
        }
        File guildConfig = new File(guildFolder, guildJacksonConfig.getGuildID() + ".yml");
        objectMapper.writeValue(guildConfig, guildJacksonConfig);
    }

}
