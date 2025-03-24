package me.furkanzhlp.timedwings.manager;

import me.furkanzhlp.timedwings.TimedWings;

public abstract class Manager {
    protected TimedWings plugin;
    public Manager(TimedWings plugin) {
        this.plugin = plugin;
    }


    public void onReload() {
    }
    public void onDisable(){}
    public void onEnable(){}


}
