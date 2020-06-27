package com.andrew121410.discord.myds;

import com.andrew121410.discord.myds.commands.manager.CommandMan;
import com.andrew121410.discord.myds.config.ConfigUtils;
import com.andrew121410.discord.myds.config.DiscordJacksonConfig;
import com.andrew121410.discord.myds.events.DiscordEvents;
import com.andrew121410.discord.myds.manager.MasterGuildManager;
import com.andrew121410.discord.myds.utils.GuildInitializer;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static final double VERSION = 0.2;

    @Getter
    private static Main instance;

    @Getter
    private transient DiscordJacksonConfig config;
    @Getter
    private JDA jda;
    @Getter
    private CommandMan commandManager;
    @Getter
    private MasterGuildManager masterGuildManager;
    @Getter
    private GuildInitializer guildInitializer;

    public static void main(String[] args) {
        new Main(args);
    }

    private Main(String[] args) {
        instance = this;
        this.config = ConfigUtils.loadMainConfig();
        setupDiscordBot();
        setupScanner();
    }

    @SneakyThrows
    private void setupDiscordBot() {
        if (this.config == null) {
            System.out.println("Please setup the config.yml file please.");
            System.exit(1);
            return;
        }
        this.commandManager = new CommandMan(this);

        this.jda = JDABuilder.createDefault(this.config.getToken())
                .setEventManager(new AnnotatedEventManager())
                .addEventListeners(this.commandManager)
                .build()
                .awaitReady();

        this.masterGuildManager = new MasterGuildManager(this);
        this.guildInitializer = new GuildInitializer(this);
        this.jda.addEventListener(new DiscordEvents(this));

        this.commandManager.registerAllCommands();

        this.jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.of(Activity.ActivityType.DEFAULT, "Waiting..."));
    }

    private void setupScanner() {
        new Thread(() -> {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    switch (line) {
                        case "end":
                        case "stop":
                        case "exit":
                        case "quit":
                            quit();
                        default:
                            System.out.println("Not a command?");
                    }
                }
            } catch (IOException ignored) {
            }
        }, "MyDiscordSocketBot-Scanner").start();
    }

    public void quit() {
        System.out.println("Shutting Down...");
        this.masterGuildManager.saveAllConfigs();
        System.exit(1);
    }
}
