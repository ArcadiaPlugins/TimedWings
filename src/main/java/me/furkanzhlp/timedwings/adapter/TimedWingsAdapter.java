package me.furkanzhlp.timedwings.adapter;

import me.furkanzhlp.timedwings.TimedWings;
import org.bukkit.entity.Player;

public abstract class TimedWingsAdapter {

    protected final TimedWings plugin;
    public TimedWingsAdapter(TimedWings plugin){
        this.plugin = plugin;
    }


    public abstract boolean canFly(Player player);

    public abstract String getAdapterName();
    public abstract String getPluginName();
    public abstract void registerAdapter();
    public abstract void registerAdapterOnLoad();
    public abstract void unRegisterAdapter();

    public boolean checkPlugin(){
        return (plugin.getServer().getPluginManager().getPlugin(getPluginName()) != null);
    }






}
