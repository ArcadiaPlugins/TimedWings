package tc.arcadia.timedwings.listeners;

import tc.arcadia.timedwings.player.PlayerDataManager;
import tc.arcadia.timedwings.TimedWings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class PlayerListeners implements Listener {


    private final TimedWings plugin;

    public PlayerListeners(TimedWings plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        PlayerDataManager playerDataManager = plugin.getPlayerDataManager();

        Player player = event.getPlayer();
        if(playerDataManager.hasPlayerData(player)) return;

        playerDataManager.loadPlayerData(player);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event){
        PlayerDataManager playerDataManager = plugin.getPlayerDataManager();

        Player player = event.getPlayer();
        if(!playerDataManager.hasPlayerData(player)) return;

        playerDataManager.unloadPlayerData(player);
    }

    @EventHandler
    public void onPlayerFlightEnable(PlayerToggleFlightEvent event){
        plugin.getFlightManager().handleFlyingPlayer(event.getPlayer());

    }


}
