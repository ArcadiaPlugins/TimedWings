package tc.arcadia.timedwings.message;

import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.language.LanguageManager;
import tc.arcadia.timedwings.utils.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class MessageManager {

    private final TimedWings plugin;

    public MessageManager(TimedWings plugin) {
        this.plugin = plugin;
    }

    public void sendLanguageMessage(CommandSender sender, String messageKey) {
        sendLanguageMessage(sender, messageKey, new MessagePlaceholder());
    }

    public void sendLanguageMessage(CommandSender sender, String messageKey, MessagePlaceholder placeholders) {
        LanguageManager languageManager = plugin.getLanguageManager();
        if (languageManager.get(sender).isList(messageKey)) {
            List<String> messages = languageManager.get(sender).getStringList(messageKey)
                    .stream()
                    .map(placeholders::replace)
                    .collect(Collectors.toList());
            sendMessage(sender, messages);
        } else {
            String message = languageManager.get(sender).getString(messageKey, "Undefined Language String: " + messageKey);
            sendMessage(sender, placeholders.replace(message));
        }
    }

    public void sendMessage(CommandSender sender,List<String> messages) {
        for (String message : messages) {
            sendMessage(sender, message);
        }
    }
    public void sendMessage(CommandSender sender, String message) {
        if (message == null || message.isEmpty()) {
            return;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (message.contains("\n") || message.contains("\\n")) {
                List<String> messages = new ArrayList<>();

                message = message.replace("\\n", "\n");

                for (String messageList : message.split("\n")) {
                    messages.add(TextUtils.colorize(messageList));
                }

                sender.sendMessage(messages.toArray(new String[0]));
            } else {
                sender.sendMessage(TextUtils.colorize(message));
            }
        } else {
            if (message.contains("\n") || message.contains("\\n")) {

                message = message.replace("\\n", "\n");

                sender.sendMessage(Arrays.stream(message.split("\n")).map(messageList -> ChatColor.stripColor(TextUtils.colorize(messageList))).toArray(String[]::new));
            } else {
                sender.sendMessage(ChatColor.stripColor(TextUtils.colorize(message)));
            }
        }
    }

//    public String replaceMessage(Player player, String message) {
//        message = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, message.replace("&", "clr")).replace("clr", "&");
//        return message;
//    }

}
