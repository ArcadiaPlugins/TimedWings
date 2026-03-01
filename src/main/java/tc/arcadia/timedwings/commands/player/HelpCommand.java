package tc.arcadia.timedwings.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.commands.Command;

public class HelpCommand extends Command {

    public HelpCommand(TimedWings plugin) {
        super(plugin);
    }

    @Override
    public void onPlayerCommand(Player player, String[] args) {
        plugin.getMessageManager().sendLanguageMessage(player, "Commands.Help.Message");
    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender sender, String[] args) {
        plugin.getMessageManager().sendLanguageMessage(sender, "Commands.Help.Message");
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean requiresPermission() {
        return false;
    }
}
