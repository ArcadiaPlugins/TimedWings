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

public class SetCommand extends Command {

    public SetCommand(TimedWings plugin) {
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

        if (args.length < 2) {
            messageManager.sendMessage(sender, languageManager.get(sender).getString("Commands.Set.Usage"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            messageManager.sendMessage(sender, languageManager.get(sender).getString("Commands.Set.Player-Not-Found"));
            return;
        }

        int seconds;
        try {
            seconds = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            messageManager.sendMessage(sender, languageManager.get(sender).getString("Commands.Set.Invalid-Amount"));
            return;
        }

        PlayerData targetData = playerDataManager.getPlayerData(target.getUniqueId());
        targetData.setRemainingFlightTime(seconds);
        targetData.save();

        String success = languageManager.get(sender).getString("Commands.Set.Success")
                .replace("%player%", target.getName())
                .replace("%seconds%", String.valueOf(seconds));
        messageManager.sendMessage(sender, success);
    }

    @Override
    public String getName() {
        return "set";
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
