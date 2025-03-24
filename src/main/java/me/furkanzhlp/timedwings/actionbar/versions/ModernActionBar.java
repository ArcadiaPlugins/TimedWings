package me.furkanzhlp.timedwings.actionbar.versions;

import me.furkanzhlp.timedwings.TimedWings;
import me.furkanzhlp.timedwings.actionbar.ActionBar;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ModernActionBar extends ActionBar {
    public ModernActionBar(TimedWings plugin) {
        super(plugin);
    }

    @Override
    public void sendActionBar(Player player, String message) {
        if (!player.isOnline()) return;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));

    }
}
