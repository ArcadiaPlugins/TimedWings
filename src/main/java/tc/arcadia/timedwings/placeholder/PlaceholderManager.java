package tc.arcadia.timedwings.placeholder;

import tc.arcadia.timedwings.manager.Manager;
import tc.arcadia.timedwings.player.PlayerData;
import tc.arcadia.timedwings.player.PlayerDataManager;
import tc.arcadia.timedwings.utils.TextUtils;
import tc.arcadia.timedwings.TimedWings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class PlaceholderManager extends Manager {

    private final boolean placeholderAPIEnabled;
    public PlaceholderManager(TimedWings plugin) {
        super(plugin);

        PluginManager pluginManager = this.plugin.getServer().getPluginManager();
        this.placeholderAPIEnabled = pluginManager.getPlugin("PlaceholderAPI") != null;

    }


    public void registerPlaceholders() {
        if (this.placeholderAPIEnabled) {
            plugin.logger().info("PlaceholderAPI detected, registering placeholders...");
            new PlaceholderAPI(this.plugin).register();
        }
    }
    public void unregisterPlaceholders() {
        if (this.placeholderAPIEnabled) {
            new PlaceholderAPI(this.plugin).unregister();
        }
    }

    public String processPlaceholder(Player player, String placeholder){
        if (player == null || placeholder == null) {
            return "";
        }
        PlayerDataManager playerDataManager = plugin.getPlayerDataManager();
        PlayerData playerData = playerDataManager.getPlayerData(player);

        FileConfiguration language = plugin.getLanguageManager().get(player);

        String returnValue = null;

        switch (placeholder){
            case "timedwings_total_flight_time":
                if(playerData != null){
                    if(playerData.getRemainingFlightTime() > 0){
                        returnValue = TextUtils.colorize(TextUtils.formatDuration(language.getString("placeholder.timedwings_total_flight_time.time","{y} {mo} {d} {h} {m} {s}"),playerData.getRemainingFlightTime(),player));
                    }else{
                        returnValue = TextUtils.colorize(language.getString("placeholder.timedwings_total_flight_time.no-time","No Time Left"));
                    }
                }else{
                    returnValue = TextUtils.colorize(language.getString("placeholder.timedwings_total_flight_time.no-time","No Time Left"));
                }
                break;
            case "timedwings_total_flight_time_seconds":
                if(playerData != null){
                    if(playerData.getRemainingFlightTime() > 0){
                        returnValue = TextUtils.colorize(language.getString("placeholder.timedwings_total_flight_time_seconds.time","{seconds}").replace("{seconds}",""+playerData.getRemainingFlightTime()));
                    }else{
                        returnValue = TextUtils.colorize(language.getString("placeholder.timedwings_total_flight_time_seconds.no-time","No Time Left"));
                    }
                }else{
                    returnValue = TextUtils.colorize(language.getString("placeholder.timedwings_total_flight_time_seconds.no-time","No Time Left"));
                }
                break;
        }

        return returnValue;
    }






}
