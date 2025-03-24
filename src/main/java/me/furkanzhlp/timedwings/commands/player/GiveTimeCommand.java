package me.furkanzhlp.timedwings.commands.player;

import me.furkanzhlp.timedwings.TimedWings;
import me.furkanzhlp.timedwings.commands.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GiveTimeCommand extends Command {
    public GiveTimeCommand(TimedWings plugin) {
        super(plugin);
    }

    @Override
    public void onPlayerCommand(Player player, String[] args) {

    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender sender, String[] args) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}
