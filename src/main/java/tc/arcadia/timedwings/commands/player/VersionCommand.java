package tc.arcadia.timedwings.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.commands.Command;
import tc.arcadia.timedwings.message.MessagePlaceholder;

public class VersionCommand extends Command {

    public VersionCommand(TimedWings plugin) {
        super(plugin);
    }

    @Override
    public void onPlayerCommand(Player player, String[] args) {
        handleCommand(player);
    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender sender, String[] args) {
        handleCommand(sender);
    }

    private void handleCommand(CommandSender sender) {
        plugin.getMessageManager().sendLanguageMessage(sender, "Commands.Version.Message",
                new MessagePlaceholder().add("version", plugin.getDescription().getVersion()));
    }

    @Override
    public String getName() {
        return "version";
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
