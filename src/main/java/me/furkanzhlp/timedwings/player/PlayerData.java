package me.furkanzhlp.timedwings.player;

import me.furkanzhlp.timedwings.TimedWings;
import me.furkanzhlp.timedwings.config.ConfigFile;
import me.furkanzhlp.timedwings.config.ConfigManager;
import me.furkanzhlp.timedwings.storage.StorageManager;
import me.furkanzhlp.timedwings.storage.StorageProvider;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerData {
    private final TimedWings plugin;
    private final UUID playerUUID;
    private int usedFlightTime;
    private int remainingFlightTime;
    private boolean isFlying;
    public PlayerData(UUID playerUUID) {
        this.plugin = TimedWings.getInstance();
        this.playerUUID = playerUUID;
        this.usedFlightTime = 0;
        this.remainingFlightTime = 0;
    }
    public void removeFlightTime(int toRemoveTime){
        this.remainingFlightTime = Math.max(remainingFlightTime-toRemoveTime,0);
    }
    public void addFlightTime(int toAddTime){
        this.remainingFlightTime += toAddTime;
    }
    public void setFlightTime(int remainingFlightTime) {
        this.remainingFlightTime = remainingFlightTime;
    }

    public int getRemainingFlightTime() {
        return remainingFlightTime;
    }


    public void setRemainingFlightTime(int remainingFlightTime) {
        this.remainingFlightTime = remainingFlightTime;
    }

    public void addUsedFlightTime(int toAdd) {
        this.usedFlightTime += toAdd;
    }
    public void removeUsedFlightTime(int toRemove) {
        this.usedFlightTime = Math.max(usedFlightTime-toRemove,0);
    }
    public void setUsedFlightTime(int usedFlightTime) {
        this.usedFlightTime = usedFlightTime;
    }

    public int getUsedFlightTime() {
        return usedFlightTime;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
    }

    public boolean isFlying() {
        return isFlying;
    }


    public void save(){
        StorageManager storageManager = plugin.getStorageManager();
        StorageProvider storageProvider = storageManager.getStorageProvider();
        storageProvider.savePlayerData(this);
    }
}
