package tc.arcadia.timedwings.adapter;

import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.adapter.adapters.fabledskyblock.FabledSkyblockAdapter;
import tc.arcadia.timedwings.adapter.adapters.worldguard.WorldGuardAdapter;
import tc.arcadia.timedwings.manager.Manager;

import java.util.*;

public class AdapterManager extends Manager {

    private final List<TimedWingsAdapter> adapterList;
    private final Map<String,TimedWingsAdapter> registeredAdapters = new HashMap<>();
    public AdapterManager(TimedWings plugin) {
        super(plugin);

        this.adapterList = Arrays.asList(
            new FabledSkyblockAdapter(plugin),
            new WorldGuardAdapter(plugin)
        );
    }

    public void onLoad(){
        adapterList.stream().filter(adapter -> adapter.checkPlugin()).forEach(this::registerAdapterOnLoad);
    }
    public void onEnable() {
        adapterList.stream().filter(adapter -> adapter.checkPlugin()).forEach(this::registerAdapter);
    }

    public void registerAdapterOnLoad(TimedWingsAdapter adapter){
        if(!registeredAdapters.containsKey(adapter.getAdapterName())){
            if(!adapter.checkPlugin()) return;
            plugin.logger().info("[Adapter] Registering "+adapter.getPluginName()+" into TimedWings plugin.");
            registeredAdapters.put(adapter.getAdapterName(),adapter);
            adapter.registerAdapterOnLoad();
        }
    }

    public void registerAdapter(TimedWingsAdapter adapter){
        adapter.registerAdapter();
    }

    public void unregisterAdapter(TimedWingsAdapter adapter){
        adapter.unRegisterAdapter();
        registeredAdapters.remove(adapter.getAdapterName());
    }

    public Map<String, TimedWingsAdapter> getRegisteredAdapters() {
        return registeredAdapters;
    }

    public void onDisable(){
        for (TimedWingsAdapter adapter : new ArrayList<>(registeredAdapters.values())) {
            unregisterAdapter(adapter);
        }
    }
}
