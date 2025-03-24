package me.furkanzhlp.timedwings.commands;

import me.furkanzhlp.timedwings.TimedWings;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class Command {

    protected final TimedWings plugin;
    public Command(TimedWings plugin) {
        this.plugin = plugin;
    }

    public abstract void onPlayerCommand(Player player, String[] args);

    public abstract void onConsoleCommand(ConsoleCommandSender sender, String[] args);

    public abstract String getName();

    public abstract String[] getAliases();



    String getDescription() { return null; }


}
