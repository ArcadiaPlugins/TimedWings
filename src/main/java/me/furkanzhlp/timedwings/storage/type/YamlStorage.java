package me.furkanzhlp.timedwings.storage.type;

import me.furkanzhlp.timedwings.TimedWings;
import me.furkanzhlp.timedwings.config.ConfigFile;
import me.furkanzhlp.timedwings.config.ConfigManager;
import me.furkanzhlp.timedwings.player.PlayerData;
import me.furkanzhlp.timedwings.storage.StorageProvider;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class YamlStorage extends StorageProvider {
    private final File folder;

    public YamlStorage(TimedWings plugin) {
        super(plugin);
        this.folder = new File(TimedWings.getInstance().getDataFolder()+"/player-data");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    @Override
    public void setup() {}

    @Override
    public boolean savePlayerData(PlayerData playerData) {
        ConfigManager configManager = plugin.getConfigManager();
        ConfigFile configFile = configManager.getConfig(new File(this.plugin.getDataFolder() + "/player-data", playerData.getPlayerUUID().toString() + ".yml"));

        configFile.getFileConfiguration().set("data.used-flight-time",playerData.getUsedFlightTime());
        configFile.getFileConfiguration().set("data.remaining-flight-time",playerData.getRemainingFlightTime());
        try {
            configFile.getFileConfiguration().save(configFile.getFile());
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public PlayerData loadPlayerData(Player player) {
        return loadPlayerData(player.getUniqueId());
    }
    @Override
    public PlayerData loadPlayerData(UUID playerUUID) {
        ConfigManager configManager = plugin.getConfigManager();

        File playerDataFolder = new File(this.plugin.getDataFolder()+"/player-data");
        if(!playerDataFolder.exists()) playerDataFolder.mkdir();
        File dataFilePath = new File(playerDataFolder, playerUUID.toString() + ".yml");
        if(configManager.isFileExist(dataFilePath)){
            ConfigFile dataFile = configManager.getConfig(dataFilePath);
            FileConfiguration playerDataConfiguration = dataFile.getFileConfiguration();
            PlayerData playerData = new PlayerData(playerUUID);
            playerData.setUsedFlightTime(playerDataConfiguration.getInt("data.used-flight-time",0));
            playerData.setRemainingFlightTime(playerDataConfiguration.getInt("data.remaining-flight-time",0));
            return playerData;
        }else{
            return new PlayerData(playerUUID);
        }
    }

    @Override
    public void close() {}
}
