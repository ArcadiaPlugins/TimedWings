package me.furkanzhlp.timedwings.actionbar;

import me.furkanzhlp.timedwings.TimedWings;
import me.furkanzhlp.timedwings.manager.Manager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class ActionBar extends Manager {

    public ActionBar(TimedWings plugin) {
        super(plugin);
    }
    public abstract void sendActionBar(Player player,String message);

    public void sendActionBar(final Player player, final String message, int duration) {
        sendActionBar(player, message);
        if (duration >= 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, "");
                }
            }.runTaskLater(plugin, duration + 1);
        }
        while (duration > 40) {
            duration -= 40;
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, message);
                }
            }.runTaskLater(plugin, (long) duration);
        }
    }

}
