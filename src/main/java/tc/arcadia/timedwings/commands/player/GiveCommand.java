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
        LanguageManager languageManager = plugin.getLanguageManager();
        PlayerDataManager playerDataManager = plugin.getPlayerDataManager();

        if (args.length < 2) {
            String usage = languageManager.get(sender).getString("Commands.Give.Usage");
            messageManager.sendMessage(sender, usage);
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            String notFound = languageManager.get(sender).getString("Commands.Give.Player-Not-Found");
            messageManager.sendMessage(sender, notFound);
            return;
        }

        int seconds;
        try {
            seconds = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            String invalid = languageManager.get(sender).getString("Commands.Give.Invalid-Amount");
            messageManager.sendMessage(sender, invalid);
            return;
        }

        PlayerData targetData = playerDataManager.getPlayerData(target.getUniqueId());
        targetData.addFlightTime(seconds);
        targetData.save();

        String success = languageManager.get(sender).getString("Commands.Give.Success")
                .replace("%player%", target.getName())
                .replace("%seconds%", String.valueOf(seconds));
        messageManager.sendMessage(sender, success);
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
