package tc.arcadia.timedwings.actionbar;

import tc.arcadia.timedwings.actionbar.versions.LegacyActionBar;
import tc.arcadia.timedwings.actionbar.versions.ModernActionBar;
import tc.arcadia.timedwings.manager.Manager;
import tc.arcadia.timedwings.TimedWings;
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
