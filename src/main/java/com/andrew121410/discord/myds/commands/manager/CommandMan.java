package com.andrew121410.discord.myds.commands.manager;

import com.andrew121410.discord.myds.Main;
import com.andrew121410.discord.myds.commands.ConfigCommand;
import com.andrew121410.discord.myds.commands.HelpCommand;
import lombok.Getter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandMan {

    @Getter
    private Map<String, IDiscordCommand> commandMap;

    private Main main;

    public CommandMan(Main main) {
        this.main = main;
        this.commandMap = new HashMap<>();
    }

    @SubscribeEvent
    public void onMessage(MessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().startsWith(this.main.getConfig().getPrefix())) {
            return;
        }

        String arg = event.getMessage().getContentRaw();
        String[] args = arg.split(" ");
        String command = args[0];
        String commandFiltered = args[0].replace(this.main.getConfig().getPrefix(), "").toLowerCase();
        args = Arrays.copyOfRange(args, 1, args.length);

        event.getChannel().sendMessage("Length: " + args.length + " ?: " + args + " Inside: " + Arrays.toString(args)).queue(); //Debug

        IDiscordCommand iDiscordCommand = this.commandMap.get(commandFiltered);
        if (iDiscordCommand != null) iDiscordCommand.onMessage(event, args);
    }

    public void registerCommand(String command, IDiscordCommand iDiscordCommand) {
        this.commandMap.putIfAbsent(command, iDiscordCommand);
    }

    public void registerAllCommands() {
        new HelpCommand(this.main);
        new ConfigCommand(this.main);
    }
}
