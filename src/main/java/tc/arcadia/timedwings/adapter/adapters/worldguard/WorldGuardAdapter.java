package tc.arcadia.timedwings.adapter.adapters.worldguard;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.adapter.TimedWingsAdapter;
import org.bukkit.entity.Player;


public class WorldGuardAdapter extends TimedWingsAdapter {

    private StateFlag timedWingsFlag;

    public WorldGuardAdapter(TimedWings plugin) {
        super(plugin);
    }

    @Override
    public boolean canFly(Player player) {
        if (timedWingsFlag == null) return true;

        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);

        if(WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(localPlayer, localPlayer.getWorld())){
            return true;
        }

        Location loc = localPlayer.getLocation();
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        return query.testState(loc, localPlayer, timedWingsFlag);
    }

    @Override
    public String getAdapterName() {
        return "WorldGuard";
    }

    @Override
    public String getPluginName() {
        return "WorldGuard";
    }

    @Override
    public void registerAdapter() {
        if(timedWingsFlag == null) return;
        plugin.logger().info(" ");
        plugin.logger().info("🌍 [TimedWings - WorldGuard Integration]");
        plugin.logger().info("The custom flag 'timedwings-fly' has been successfully registered.");
        plugin.logger().info(" ");
        plugin.logger().info("Usage:");
        plugin.logger().info("/rg flag <region-name> timedwings-fly allow   → Enables TimedWings flight in that region");
        plugin.logger().info("/rg flag <region-name> timedwings-fly deny    → Disables TimedWings flight in that region");
        plugin.logger().info(" ");
        plugin.logger().info("When set to 'deny', players will not be able to use flight via TimedWings even if they have time.");
        plugin.logger().info(" ");
    }
    @Override
    public void registerAdapterOnLoad() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            StateFlag flag = new StateFlag("timedwings-fly", true);
            registry.register(flag);
            timedWingsFlag = flag;
            plugin.logger().info("(WorldGuard) Successfully registered custom flag: timedwings-fly");
        } catch (FlagConflictException e) {
            Flag<?> existing = registry.get("timedwings-fly");
            if (existing instanceof StateFlag) {
                timedWingsFlag = (StateFlag) existing;
                plugin.logger().error("(WorldGuard) Flag 'timedwings-fly' already exists, using existing flag.");
            } else {
                plugin.logger().error("(WorldGuard) Conflict detected: 'timedwings-fly' is not a StateFlag. TimedWings integration may not work properly.");
            }
        }
    }

    @Override
    public void unRegisterAdapter() {

    }

}
