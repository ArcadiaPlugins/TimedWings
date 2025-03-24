package me.furkanzhlp.timedwings.storage;

public enum StorageType {

    YAML("yaml"),
    SQLITE("sqlite"),
    MYSQL("mysql"),
    H2("h2");

    private final String friendlyName;

    StorageType(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public static StorageType fromString(String type) {
        try {
            return StorageType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return YAML; // Varsayılan olarak YAML kullan
        }
    }

    public static StorageType fromFriendlyName(String friendlyName) {
        for (StorageType type : values()) {
            if (type.friendlyName.equalsIgnoreCase(friendlyName)) {
                return type;
            }
        }
        return YAML; // Varsayılan olarak YAML kullan
    }
}
