package tc.arcadia.timedwings.migrate.migrators;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.migrate.PluginMigrator;
import tc.arcadia.timedwings.player.PlayerData;
import tc.arcadia.timedwings.player.PlayerDataManager;

import java.io.File;
import java.util.UUID;

public class TempFlyMigrator extends PluginMigrator {

    private final File dataFile;

    public TempFlyMigrator(TimedWings plugin) {
        super(plugin);
        this.dataFile = new File(plugin.getDataFolder().getParentFile(), "TempFly/data.yml");
    }

    @Override
    public String getPluginName() {
        return "TempFly";
    }

    @Override
    public boolean isCompatible() {
        return dataFile.exists();
    }

    @Override
    public void migrateData() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if (!dataFile.exists()) {
                    plugin.logger().warning("[TempFly-Migration] TempFly data.yml not found.");
                    return;
                }

                YamlConfiguration config = YamlConfiguration.loadConfiguration(dataFile);

                if (!config.contains("players")) {
                    plugin.logger().warning("[TempFly-Migration] No 'players' section found in TempFly data.yml.");
                    return;
                }
                PlayerDataManager playerDataManager = plugin.getPlayerDataManager();

                int imported = 0;
                for (String uuidStr : config.getConfigurationSection("players").getKeys(false)) {
                    try {
                        UUID uuid = UUID.fromString(uuidStr);
                        int time = (int) config.getDouble("players." + uuidStr + ".time", 0.0);

                        PlayerData playerData = playerDataManager.loadPlayerData(uuid);
                        playerData.setRemainingFlightTime(time);
                        playerData.save();

                        imported++;
                    } catch (Exception e) {
                        plugin.logger().warning("[TempFly-Migration] Failed to import player: " + uuidStr);
                        e.printStackTrace();
                    }
                }

                plugin.logger().info("[TempFly-Migration] TempFly migration completed. Imported " + imported + " players.");
            }
        });

    }
}
