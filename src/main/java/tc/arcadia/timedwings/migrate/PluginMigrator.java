package tc.arcadia.timedwings.migrate;

import tc.arcadia.timedwings.TimedWings;

public abstract class PluginMigrator {

    protected final TimedWings plugin;

    public PluginMigrator(TimedWings plugin) {
        this.plugin = plugin;
    }

    public abstract String getPluginName();

    public abstract boolean isCompatible();

    public abstract void migrateData();

}
