package tc.arcadia.timedwings.storage;


import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.player.PlayerData;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class StorageProvider {

    protected final TimedWings plugin;
    public StorageProvider(TimedWings plugin) {
        this.plugin = plugin;
    }

    public abstract void setup();
    public abstract boolean savePlayerData(PlayerData playerData);
    public abstract PlayerData loadPlayerData(Player player);
    public abstract PlayerData loadPlayerData(UUID playerUUID);
    public abstract void close();
}
