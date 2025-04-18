package tc.arcadia.timedwings.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.commands.Command;
import tc.arcadia.timedwings.language.LanguageManager;
import tc.arcadia.timedwings.message.MessageManager;
import tc.arcadia.timedwings.message.MessagePlaceholder;
import tc.arcadia.timedwings.player.PlayerData;
import tc.arcadia.timedwings.player.PlayerDataManager;
import tc.arcadia.timedwings.utils.TextUtils;

public class GiveCommand extends Command {

    public GiveCommand(TimedWings plugin) {
        super(plugin);
    }

    @Override
    public void onPlayerCommand(Player sender, String[] args) {
        handleCommand(sender, args);
    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender sender, String[] args) {
        handleCommand(sender, args);
    }

    private void handleCommand(CommandSender sender, String[] args) {
        MessageManager messageManager = plugin.getMessageManager();
        PlayerDataManager playerDataManager = plugin.getPlayerDataManager();

        if (args.length < 2) {
            messageManager.sendLanguageMessage(sender, "Commands.Give.Usage");
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            messageManager.sendLanguageMessage(sender, "Commands.Give.Player-Not-Found");
            return;
        }

        int seconds;
        try {
            seconds = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            messageManager.sendLanguageMessage(sender, "Commands.Give.Invalid-Amount");
            return;
        }

        PlayerData targetData = playerDataManager.getPlayerData(target.getUniqueId());
        targetData.addFlightTime(seconds);
        targetData.save();

        messageManager.sendLanguageMessage(sender,"Commands.Give.Success", new MessagePlaceholder()
            .add("player", target.getName())
            .add("seconds", String.valueOf(seconds)));
    }

    @Override
    public String getName() {
        return "give";
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
