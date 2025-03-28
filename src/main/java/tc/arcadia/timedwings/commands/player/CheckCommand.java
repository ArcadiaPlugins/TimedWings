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
import tc.arcadia.timedwings.utils.TextUtils;

public class CheckCommand extends Command {

    public CheckCommand(TimedWings plugin) {
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
            messageManager.sendMessage(sender, languageManager.get(sender).getString("Commands.Check.Usage"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            messageManager.sendMessage(sender, languageManager.get(sender).getString("Commands.Check.Player-Not-Found"));
            return;
        }

        PlayerData data = playerDataManager.getPlayerData(target.getUniqueId());
        String formattedTime = TextUtils.formatDuration(
                languageManager.get(sender).getString("Commands.Check.Format", "{h} {m} {s}"),
                data.getRemainingFlightTime(),
                target
        );

        String message = languageManager.get(sender).getString("Commands.Check.Result")
                .replace("%player%", target.getName())
                .replace("%time%", formattedTime);

        messageManager.sendMessage(sender, message);
    }

    @Override
    public String getName() {
        return "check";
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
