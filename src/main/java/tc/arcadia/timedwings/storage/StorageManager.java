package tc.arcadia.timedwings.storage;

import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.storage.type.H2Storage;
import tc.arcadia.timedwings.storage.type.MysqlStorage;
import tc.arcadia.timedwings.storage.type.SqliteStorage;
import tc.arcadia.timedwings.storage.type.YamlStorage;

public class StorageManager {
    private final TimedWings plugin;
    private StorageType storageType;
    private StorageProvider storageProvider;

    public StorageManager(TimedWings plugin) {
        this.plugin = plugin;
        String storageTypeString = plugin.getConfiguration().getString("general.storage.type");
        storageType = StorageType.fromString(storageTypeString);

        plugin.logger().info("Selected storage type: "+storageType);
        switch (storageType) {
            case YAML:
                storageProvider = new YamlStorage(plugin);
                break;
            case SQLITE:
                storageProvider = new SqliteStorage(plugin);
                break;
            case MYSQL:
                storageProvider = new MysqlStorage(plugin);
                break;
            case H2:
                storageProvider = new H2Storage(plugin);
                break;
            default:
                storageProvider = new YamlStorage(plugin);
                break;
        }
        storageProvider.setup();
    }
    public StorageType getStorageType() {
        return storageType;
    }

    public StorageProvider getStorageProvider() {
        return storageProvider;
    }

}
