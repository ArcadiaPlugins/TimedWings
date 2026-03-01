package tc.arcadia.timedwings.storage.type;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.player.PlayerData;
import tc.arcadia.timedwings.storage.StorageProvider;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class MysqlStorage extends StorageProvider {
    private HikariDataSource dataSource;

    public MysqlStorage(TimedWings plugin) {
        super(plugin);
        setup();
    }

    @Override
    public void setup() {
        String host = plugin.getConfiguration().getString("general.storage.mysql.host");
        String port = plugin.getConfiguration().getString("general.storage.mysql.port");
        String database = plugin.getConfiguration().getString("general.storage.mysql.database");
        String username = plugin.getConfiguration().getString("general.storage.mysql.username");
        String password = plugin.getConfiguration().getString("general.storage.mysql.password");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&autoReconnect=true";

        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(300000);
            config.setMaxLifetime(600000);
            config.setConnectionTimeout(10000);
            config.setPoolName("TimedWings-MySQL");

            dataSource = new HikariDataSource(config);

            try (Connection connection = dataSource.getConnection();
                 Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS timedwings_player_data (" +
                        "uuid VARCHAR(36) PRIMARY KEY, " +
                        "used_flight_time INT, " +
                        "remaining_flight_time INT" +
                        ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            plugin.logger().error("MySQL connection failed: " + e.getMessage());
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    @Override
    public boolean savePlayerData(PlayerData playerData) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO timedwings_player_data (uuid, used_flight_time, remaining_flight_time) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                        "used_flight_time = VALUES(used_flight_time), " +
                        "remaining_flight_time = VALUES(remaining_flight_time);"
        )) {
            stmt.setString(1, playerData.getPlayerUUID().toString());
            stmt.setInt(2, playerData.getUsedFlightTime());
            stmt.setInt(3, playerData.getRemainingFlightTime());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PlayerData loadPlayerData(Player player) {
        return loadPlayerData(player.getUniqueId());
    }

    @Override
    public PlayerData loadPlayerData(UUID playerUUID) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                "SELECT used_flight_time, remaining_flight_time FROM timedwings_player_data WHERE uuid = ?"
        )) {
            stmt.setString(1, playerUUID.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                PlayerData playerData = new PlayerData(playerUUID);
                if (rs.next()) {
                    playerData.setUsedFlightTime(rs.getInt("used_flight_time"));
                    playerData.setRemainingFlightTime(rs.getInt("remaining_flight_time"));
                }
                return playerData;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new PlayerData(playerUUID);
        }
    }

    @Override
    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
