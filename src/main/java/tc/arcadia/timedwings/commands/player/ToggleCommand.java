package tc.arcadia.timedwings.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.commands.Command;
import tc.arcadia.timedwings.message.MessagePlaceholder;
import tc.arcadia.timedwings.player.PlayerData;
import tc.arcadia.timedwings.player.PlayerDataManager;

public class ToggleCommand extends Command {

    public ToggleCommand(TimedWings plugin) {
        super(plugin);
    }

    @Override
    public void onPlayerCommand(Player sender, String[] args) {
        if (args.length >= 1) {
            handleCommand(sender, args);
        } else {
            toggleFlight(sender, sender);
        }
    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender sender, String[] args) {
        handleCommand(sender, args);
    }

    private void handleCommand(CommandSender sender, String[] args) {
        if (args.length < 1) {
            plugin.getMessageManager().sendLanguageMessage(sender, "Commands.Toggle.Usage");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            plugin.getMessageManager().sendLanguageMessage(sender, "Commands.Toggle.Player-Not-Found");
            return;
        }

        toggleFlight(sender, target);
    }

    private void toggleFlight(CommandSender sender, Player target) {
        PlayerDataManager playerDataManager = plugin.getPlayerDataManager();
        PlayerData data = playerDataManager.getPlayerData(target.getUniqueId());

        if (data.getRemainingFlightTime() <= 0) {
            plugin.getMessageManager().sendLanguageMessage(sender, "Commands.Toggle.No-Time");
            return;
        }

        boolean newState = !target.getAllowFlight();
        target.setAllowFlight(newState);
        if (!newState) target.setFlying(false);

        String stateKey = newState ? "Commands.Toggle.Enabled" : "Commands.Toggle.Disabled";
        plugin.getMessageManager().sendLanguageMessage(sender, stateKey,
                new MessagePlaceholder().add("player", target.getName()));
    }

    @Override
    public String getName() {
        return "toggle";
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
