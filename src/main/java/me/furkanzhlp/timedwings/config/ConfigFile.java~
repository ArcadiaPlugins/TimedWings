package me.furkanzhlp.timedwings.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;

public class ConfigFile {
    private final File configFile;
    private FileConfiguration configLoad;

    public ConfigFile(ConfigManager configManager, java.io.File configPath) {
        this.configFile = configPath;

        if (configPath.getName().equals("config.yml")) {
            this.configLoad = YamlConfiguration.loadConfiguration(new InputStreamReader(configManager.getConfigContent(this.configFile)));
        } else {
            this.configLoad = YamlConfiguration.loadConfiguration(configPath);
        }
    }

    public File getFile() {
        return this.configFile;
    }

    public FileConfiguration getFileConfiguration() {
        return this.configLoad;
    }

    public FileConfiguration loadFile() {
        this.configLoad = YamlConfiguration.loadConfiguration(this.configFile);

        return this.configLoad;
    }
}
