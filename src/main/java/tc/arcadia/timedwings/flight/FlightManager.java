package tc.arcadia.timedwings.flight;

import tc.arcadia.timedwings.manager.Manager;
import tc.arcadia.timedwings.player.PlayerData;
import tc.arcadia.timedwings.player.PlayerDataManager;
import tc.arcadia.timedwings.utils.TextUtils;
import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.adapter.TimedWingsAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class FlightManager extends Manager {

    private final PlayerDataManager playerDataManager;
    private BukkitTask flightCheckTask;
    private String durationFormat;

    public FlightManager(TimedWings plugin) {
        super(plugin);
        playerDataManager = plugin.getPlayerDataManager();

        initializeData();
        startFlightCheckTask();
    }

    public void initializeData(){
        this.durationFormat = plugin.getConfiguration().getString("general.action-bar.duration-format");
    }

    public void startFlightCheckTask(){
        flightCheckTask = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                handleFlyingPlayer(player);
            }
        }, 0L, 20L);
    }

    public void stopFlightCheckTask(){
        if (flightCheckTask != null) flightCheckTask.cancel();
    }

    public void handleFlyingPlayer(Player player){
        if (!playerDataManager.hasPlayerData(player)) return;
        PlayerData playerData = playerDataManager.getPlayerData(player);
        if(plugin.getConfiguration().getStringList("general.disabled-worlds").contains(player.getWorld().getName())) return;


        if(player.isFlying()){
            if (!plugin.getAdapterManager().getRegisteredAdapters().isEmpty()) {
                for (TimedWingsAdapter adapter : plugin.getAdapterManager().getRegisteredAdapters().values()) {
                    if (!adapter.canFly(player)) {
                        plugin.getMessageManager().sendLanguageMessage(player, "Adapters." + adapter.getPluginName() + ".Flight-Disabled");
                        player.setFlying(false);
                        player.setAllowFlight(false);
                        return;
                    }
                }
            }

            if (playerData.getRemainingFlightTime() > 0) {
                playerData.addUsedFlightTime(1);
                playerData.removeFlightTime(1);
            } else {
                player.setFlying(false);
                player.setAllowFlight(false);
            }
        }else{
            if(plugin.getConfiguration().getBoolean("general.auto-fly",false)) {
                if(playerData.getRemainingFlightTime() > 0 && !player.getAllowFlight()){
                    boolean canFly = true;
                    if (!plugin.getAdapterManager().getRegisteredAdapters().isEmpty()) {
                        for (TimedWingsAdapter adapter : plugin.getAdapterManager().getRegisteredAdapters().values()) {
                            if (!adapter.canFly(player)) {
                                canFly = false;
                                break;
                            }
                        }
                    }
                    player.setAllowFlight(canFly);
                }
            }
        }

        if (plugin.getConfiguration().getBoolean("general.action-bar.enabled")) {
            if(plugin.getConfiguration().getBoolean("general.action-bar.always-display") || player.isFlying()){
                String actionBarMessage = plugin.getLanguageManager().get(player).getString("action-bar.message");
                String durationFormat = this.durationFormat;

                String formattedDuration = TextUtils.formatDuration(durationFormat, playerData.getRemainingFlightTime(),player);
                actionBarMessage = actionBarMessage.replace("%duration%", formattedDuration);

                if(player.getAllowFlight()){
                    actionBarMessage = actionBarMessage.replace("%flightmode%", plugin.getLanguageManager().get(player).getString("action-bar.flight-mode.enabled"));
                }else{
                    actionBarMessage = actionBarMessage.replace("%flightmode%", plugin.getLanguageManager().get(player).getString("action-bar.flight-mode.disabled"));
                }

                plugin.getActionBarManager().getActionBar().sendActionBar(player, TextUtils.colorize(actionBarMessage));
            }
        }


    }

    public void onDisable(){
        stopFlightCheckTask();
    }

}
