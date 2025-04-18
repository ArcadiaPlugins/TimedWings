package tc.arcadia.timedwings.migrate;

import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.migrate.migrators.TempFlyMigrator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MigrationManager {

    private final TimedWings plugin;
    private final Map<String, PluginMigrator> migrators = new HashMap<>();

    public MigrationManager(TimedWings plugin) {
        this.plugin = plugin;
        registerMigrators();
    }

    private void registerMigrators() {
        PluginMigrator tempFlyMigrator = new TempFlyMigrator(plugin);
        if (tempFlyMigrator.isCompatible()) {
            migrators.put(tempFlyMigrator.getPluginName().toLowerCase(), tempFlyMigrator);
        }
    }

    public boolean migrateFrom(String pluginName) {
        PluginMigrator migrator = migrators.get(pluginName.toLowerCase());
        if (migrator == null) return false;

        migrator.migrateData();
        return true;
    }

    public Set<String> getAvailableMigrators() {
        return migrators.keySet();
    }
}
