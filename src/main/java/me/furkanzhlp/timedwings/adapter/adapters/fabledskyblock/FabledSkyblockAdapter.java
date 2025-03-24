package me.furkanzhlp.timedwings.adapter.adapters.fabledskyblock;

import com.craftaro.skyblock.SkyBlock;
import com.craftaro.skyblock.island.Island;
import com.craftaro.skyblock.island.IslandRole;
import com.craftaro.skyblock.permission.BasicPermission;
import me.furkanzhlp.timedwings.TimedWings;
import me.furkanzhlp.timedwings.adapter.TimedWingsAdapter;
import org.bukkit.entity.Player;

public class FabledSkyblockAdapter extends TimedWingsAdapter {


    public FabledSkyblockAdapter(TimedWings plugin) {
        super(plugin);
    }

    @Override
    public String getAdapterName() {
        return "FabledSkyBlock";
    }

    @Override
    public String getPluginName() {
        return "FabledSkyBlock";
    }

    @Override
    public boolean canFly(Player player) {
        Island inIsland = SkyBlock.getInstance().getIslandManager().getIslandAtLocation(player.getLocation());
        if(inIsland != null){
            IslandRole islandRole = inIsland.getRole(player);
            BasicPermission permission = SkyBlock.getInstance().getPermissionManager().getPermission("TimedWingsFly");
            return inIsland.hasPermission(islandRole,permission);
        }
        return true;
    }

    @Override
    public void registerAdapter(){
        if(SkyBlock.getInstance().getPermissionManager().getPermission("TimedWingsFly") == null){
            SkyBlock.getInstance().getPermissionManager().registerPermission(new FabledSkyBlockPermission());

            plugin.logger().info(" ");
            plugin.logger().info("🌍 [TimedWings - FabledSkyBlock Integration]");
            plugin.logger().info("FabledSkyBlock detected. TimedWings integration is active.");
            plugin.logger().info("A custom island setting 'Flight' has been added.");
            plugin.logger().info("Island owners can toggle this setting to allow flight for island members.");
            plugin.logger().info(" ");
            plugin.logger().info("To customize the name of the setting, edit the language file in FabledSkyBlock:");
            plugin.logger().info("Menu.Settings.Default.Item.Setting.TimedWingsFly.Displayname");
            plugin.logger().info(" ");

        }
    }

    @Override
    public void registerAdapterOnLoad() {

    }

    @Override
    public void unRegisterAdapter(){}



}
