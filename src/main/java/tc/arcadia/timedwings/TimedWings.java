package tc.arcadia.timedwings;

import tc.arcadia.timedwings.actionbar.ActionBarManager;
import tc.arcadia.timedwings.flight.FlightManager;
import tc.arcadia.timedwings.listeners.PlayerListeners;
import tc.arcadia.timedwings.logger.Logger;
import tc.arcadia.timedwings.message.MessageManager;
import tc.arcadia.timedwings.placeholder.PlaceholderManager;
import tc.arcadia.timedwings.player.PlayerDataManager;
import tc.arcadia.timedwings.adapter.AdapterManager;
import tc.arcadia.timedwings.config.ConfigManager;
import tc.arcadia.timedwings.language.LanguageManager;
import tc.arcadia.timedwings.storage.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class TimedWings extends JavaPlugin {
    private static TimedWings instance;
    private ConfigManager configManager;
    private PlayerDataManager playerDataManager;
    private FlightManager flightManager;
    private PlaceholderManager placeholderManager;
    private ActionBarManager actionBarManager;
    private AdapterManager adapterManager;
    private MessageManager messageManager;
    private StorageManager storageManager;
    private LanguageManager languageManager;
    private Logger logger;
    private FileConfiguration config;


    @Override
    public void onLoad() {
        instance = this;

        logger = new Logger("TimedWings",false);

        adapterManager = new AdapterManager(this);
        adapterManager.onLoad();
    }

    @Override
    public void onEnable() {

        configManager = new ConfigManager(this);
        if (!loadConfigs()) {
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }


        languageManager = new LanguageManager(this);
        messageManager = new MessageManager(this);
        actionBarManager = new ActionBarManager(this);

        //
        storageManager = new StorageManager(this);

        playerDataManager = new PlayerDataManager(this);
        flightManager = new FlightManager(this);

        placeholderManager = new PlaceholderManager(this);
        placeholderManager.registerPlaceholders();

//        adapterManager = new AdapterManager(this);
        adapterManager.onEnable();

        Bukkit.getPluginManager().registerEvents(new PlayerListeners(this),this);

        logger.log("TimedWings plugin has been successfully enabled on version "+getDescription().getVersion());
    }


    @Override
    public void onDisable(){
        configManager.onDisable();
        playerDataManager.onDisable();
        flightManager.onDisable();
        adapterManager.onDisable();
        placeholderManager.unregisterPlaceholders();
    }

    public boolean loadConfigs(){
        try {
            this.config = this.getConfigManager().getConfig(new File(this.getDataFolder(), "config.yml")).getFileConfiguration();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }




    public FileConfiguration getConfiguration() {
        return config;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public PlaceholderManager getPlaceholderManager() {
        return placeholderManager;
    }

    public FlightManager getFlightManager() {
        return flightManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public ActionBarManager getActionBarManager() {
        return actionBarManager;
    }

    public AdapterManager getAdapterManager() {
        return adapterManager;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public Logger logger() {
        return logger;
    }

    public static TimedWings getInstance() {
        return instance;
    }
}
