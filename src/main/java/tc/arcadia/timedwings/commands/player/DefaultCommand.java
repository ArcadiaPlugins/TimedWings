package tc.arcadia.timedwings.commands.player;

import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.commands.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tc.arcadia.timedwings.language.LanguageManager;
import tc.arcadia.timedwings.message.MessageManager;
import tc.arcadia.timedwings.player.PlayerData;
import tc.arcadia.timedwings.player.PlayerDataManager;
import tc.arcadia.timedwings.utils.TextUtils;

public class DefaultCommand extends Command {
    public DefaultCommand(TimedWings plugin) {
        super(plugin);
    }

    @Override
    public boolean requiresPermission() {
        return false;
    }

    @Override
    public void onPlayerCommand(Player player, String[] args) {
        PlayerDataManager playerDataManager = plugin.getPlayerDataManager();
        LanguageManager languageManager = plugin.getLanguageManager();
        MessageManager messageManager = plugin.getMessageManager();

        if (args.length > 0) {
            String message = languageManager.get(player).getString("Commands.Default.Usage");
            messageManager.sendMessage(player, message);
            return;
        }
        PlayerData playerData = playerDataManager.getPlayerData(player.getUniqueId());

        String timeFormat = languageManager.get(player).getString("Commands.Default.Format", "{h} {m} {s}");
        String message;
        if (playerData.getRemainingFlightTime() > 0) {
            message = languageManager.get(player).getString("Commands.Default.Time-Left");
        } else {
            message = languageManager.get(player).getString("Commands.Default.No-Time");
        }
        String formattedTime = TextUtils.formatDuration(timeFormat, playerData.getRemainingFlightTime(), player);
        message = message.replace("%time-left%", formattedTime);
        messageManager.sendMessage(player, message);
    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender sender, String[] args) {
        plugin.getMessageManager().sendLanguageMessage(sender, "Commands.Console-Only");
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
