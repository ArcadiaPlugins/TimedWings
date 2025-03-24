package tc.arcadia.timedwings.adapter.adapters.bentobox;

import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.adapter.TimedWingsAdapter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.flags.Flag;

public class BentoBoxAdapter extends TimedWingsAdapter {

    private Flag timedWingsFlag;
    public BentoBoxAdapter(TimedWings plugin) {
        super(plugin);
    }

    @Override
    public boolean canFly(Player player) {
        return false;
    }

    @Override
    public String getAdapterName() {
        return "BentoBox";
    }

    @Override
    public String getPluginName() {
        return "BentoBox";
    }

    @Override
    public void registerAdapter() {
        BentoBox bentoBox = BentoBox.getInstance();
        timedWingsFlag = new Flag.Builder("TimedWingsFly",Material.FEATHER).build();
        bentoBox.getFlagsManager().registerFlag(timedWingsFlag);
        plugin.logger().info(" ");
        plugin.logger().info("🌍 [TimedWings - BentoBox Integration]");
        plugin.logger().info("BentoBox detected. TimedWings integration is active.");
        plugin.logger().info("Island settings will control whether players can fly on their own islands.");
        plugin.logger().info(" ");
    }

    @Override
    public void registerAdapterOnLoad() {}

    @Override
    public void unRegisterAdapter() {
    }
}
