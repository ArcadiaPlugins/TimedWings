package me.furkanzhlp.timedwings.actionbar;

import me.furkanzhlp.timedwings.TimedWings;
import me.furkanzhlp.timedwings.actionbar.versions.LegacyActionBar;
import me.furkanzhlp.timedwings.actionbar.versions.ModernActionBar;
import me.furkanzhlp.timedwings.manager.Manager;
import org.bukkit.Bukkit;

public class ActionBarManager extends Manager {
    private final ActionBar actionBar;
    public ActionBarManager(TimedWings plugin) {
        super(plugin);


        if (Bukkit.getServer().getVersion().matches(".*1\\.(?!10|11)\\d{2,}.*")) {
            actionBar = new ModernActionBar(plugin);
        } else {
            actionBar = new LegacyActionBar(plugin);
        }
    }

    public ActionBar getActionBar() {
        return actionBar;
    }
}
