package tc.arcadia.timedwings.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.commands.Command;
import tc.arcadia.timedwings.message.MessageManager;

public class ReloadCommand extends Command {

    public ReloadCommand(TimedWings plugin) {
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
        MessageManager messageManager = plugin.getMessageManager();

        plugin.loadConfigs();
        plugin.getLanguageManager().reload();
        plugin.getFlightManager().initializeData();

        messageManager.sendLanguageMessage(sender, "Commands.Reload.Success");
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean requiresPermission() {
        return true;
    }
}
