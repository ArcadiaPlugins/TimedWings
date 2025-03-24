package me.furkanzhlp.timedwings.storage.type;

import me.furkanzhlp.timedwings.TimedWings;
import me.furkanzhlp.timedwings.player.PlayerData;
import me.furkanzhlp.timedwings.storage.StorageProvider;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.*;
import java.util.UUID;

public class SqliteStorage extends StorageProvider {
    private Connection connection;

    public SqliteStorage(TimedWings plugin) {
        super(plugin);
        setup();
    }

    @Override
    public void setup() {
        try {
            File dbFile = new File(plugin.getDataFolder(), "timedwings_sqlite.db");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());

            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS timedwings_player_data (" +
                    "uuid TEXT PRIMARY KEY, " +
                    "used_flight_time INTEGER, " +
                    "remaining_flight_time INTEGER" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean savePlayerData(PlayerData playerData) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO timedwings_player_data (uuid, used_flight_time, remaining_flight_time) " +
                            "VALUES (?, ?, ?) " +
                            "ON CONFLICT(uuid) DO UPDATE SET used_flight_time = excluded.used_flight_time, remaining_flight_time = excluded.remaining_flight_time;"
            );
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
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT used_flight_time, remaining_flight_time FROM timedwings_player_data WHERE uuid = ?"
            );
            stmt.setString(1, playerUUID.toString());
            ResultSet rs = stmt.executeQuery();

            PlayerData playerData = new PlayerData(playerUUID);
            if (rs.next()) {
                playerData.setUsedFlightTime(rs.getInt("used_flight_time"));
                playerData.setRemainingFlightTime(rs.getInt("remaining_flight_time"));
            }
            return playerData;

        } catch (SQLException e) {
            e.printStackTrace();
            return new PlayerData(playerUUID);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
