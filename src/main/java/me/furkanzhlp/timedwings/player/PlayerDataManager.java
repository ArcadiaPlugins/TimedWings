package me.furkanzhlp.timedwings.player;

import me.furkanzhlp.timedwings.TimedWings;
import me.furkanzhlp.timedwings.manager.Manager;
import me.furkanzhlp.timedwings.storage.StorageManager;
import me.furkanzhlp.timedwings.storage.StorageProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager extends Manager {

    private final Map<UUID,PlayerData> cachedPlayers = new HashMap<>();
    public PlayerDataManager(TimedWings plugin) {
        super(plugin);

        loadAllPlayers();
    }

    public void loadAllPlayers(){
        plugin.logger().debug("----------------------------");
        plugin.logger().debug("Loading all online players...");
        Bukkit.getOnlinePlayers().forEach(this::loadPlayerData);
        plugin.logger().debug("----------------------------");
    }

    /*
        Player Data Loader
    */
    public PlayerData loadPlayerData(UUID playerUUID){
        if(cachedPlayers.containsKey(playerUUID)) return cachedPlayers.get(playerUUID);

        StorageManager storageManager = plugin.getStorageManager();

        StorageProvider storageProvider = storageManager.getStorageProvider();

        plugin.logger().debug("Loading player data by UUID: "+playerUUID);
        PlayerData playerData = storageProvider.loadPlayerData(playerUUID);
        cachedPlayers.put(playerUUID,playerData);

        return playerData;
    }
    public PlayerData loadPlayerData(Player player){
        plugin.logger().debug("Loading player data by Player: "+player.getName());
        return loadPlayerData(player.getUniqueId());
    }
    /*
        Player Data Unloader
    */
    public void unloadPlayerData(UUID playerUUID){
        if(!cachedPlayers.containsKey(playerUUID)) return;

        PlayerData playerData = cachedPlayers.get(playerUUID);
        playerData.save();

        cachedPlayers.remove(playerUUID);
    }
    public void unloadPlayerData(Player player){
        plugin.logger().debug("Unloading player data by Player: "+player.getName());
    }

    /*
        Player Data
    */
    public PlayerData getPlayerData(UUID playerUUID){
        if(!cachedPlayers.containsKey(playerUUID)) {
            return loadPlayerData(playerUUID);
        }
        return cachedPlayers.get(playerUUID);
    }

    public PlayerData getPlayerData(Player player){
        return getPlayerData(player.getUniqueId());
    }

    /*
        Controls
    */

    public boolean hasPlayerData(UUID playerUUID){
        return cachedPlayers.containsKey(playerUUID);
    }
    public boolean hasPlayerData(Player player){
        return hasPlayerData(player.getUniqueId());
    }

    public void onDisable(){
        cachedPlayers.values().forEach(PlayerData::save);
    }
}
