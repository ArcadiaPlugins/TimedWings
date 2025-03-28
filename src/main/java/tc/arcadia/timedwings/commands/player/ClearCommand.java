package tc.arcadia.timedwings.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.commands.Command;
import tc.arcadia.timedwings.language.LanguageManager;
import tc.arcadia.timedwings.message.MessageManager;
import tc.arcadia.timedwings.player.PlayerData;
import tc.arcadia.timedwings.player.PlayerDataManager;

public class ClearCommand extends Command {

    public ClearCommand(TimedWings plugin) {
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
        LanguageManager languageManager = plugin.getLanguageManager();
        PlayerDataManager playerDataManager = plugin.getPlayerDataManager();

        if (args.length < 1) {
            messageManager.sendMessage(sender, languageManager.get(sender).getString("Commands.Clear.Usage"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            messageManager.sendMessage(sender, languageManager.get(sender).getString("Commands.Clear.Player-Not-Found"));
            return;
        }

        PlayerData data = playerDataManager.getPlayerData(target.getUniqueId());
        data.setRemainingFlightTime(0);
        data.save();

        String message = languageManager.get(sender).getString("Commands.Clear.Success")
                .replace("%player%", target.getName());
        messageManager.sendMessage(sender, message);
    }

    @Override
    public String getName() {
        return "clear";
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
