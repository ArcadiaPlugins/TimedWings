package tc.arcadia.timedwings.commands.player;

import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.commands.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class DefaultCommand extends Command {
    public DefaultCommand(TimedWings plugin) {
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
        return "default";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}
