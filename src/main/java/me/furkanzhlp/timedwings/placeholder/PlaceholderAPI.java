package me.furkanzhlp.timedwings.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.furkanzhlp.timedwings.TimedWings;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPI extends PlaceholderExpansion {
    private final TimedWings plugin;

    public PlaceholderAPI(TimedWings plugin) {
        this.plugin = plugin;
    }

    public @NotNull String getIdentifier() {
        return "timedwings";
    }

    public @NotNull String getAuthor() {
        return "FurkanZhlp";
    }

    public @NotNull String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    public String onPlaceholderRequest(Player player, @NotNull String params) {
        return plugin.getPlaceholderManager().processPlaceholder(player,"timedwings_"+params);
    }
}
