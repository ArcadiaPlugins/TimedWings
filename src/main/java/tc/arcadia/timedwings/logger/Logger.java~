package me.furkanzhlp.timedwings.logger;

import org.bukkit.Bukkit;

public class Logger {
    private String pluginName;
    private boolean debugMode;
    public Logger(String pluginName,boolean debugMode){

        this.pluginName = pluginName;
        this.debugMode = debugMode;

    }

    public void log(String message, Level logLevel){
        switch (logLevel){
            case DEBUG:
                debug(message);
                break;
            case ERROR:
                error(message);
                break;
            case NORMAL:
                log(message);
                break;
            case WARNING:
                warning(message);
                break;
            case INFO:
                info(message);
                break;
        }
    }


    public void debug(String message){
        if(debugMode) Bukkit.getConsoleSender().sendMessage("§e["+pluginName+" - DEBUG] "+message.replaceAll("&","§"));
    }
    public void log(String message){
        Bukkit.getConsoleSender().sendMessage("§7["+pluginName+"] "+message.replaceAll("&","§"));
    }
    public void error(String message){
        Bukkit.getConsoleSender().sendMessage("§c["+pluginName+" - ERROR] "+message.replaceAll("&","§"));
    }
    public void warning(String message){
        Bukkit.getConsoleSender().sendMessage("§6["+pluginName+" - WARNING] "+message.replaceAll("&","§"));
    }
    public void info(String message){
        Bukkit.getConsoleSender().sendMessage("§a["+pluginName+" - INFO] "+message.replaceAll("&","§"));
    }
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public enum Level{
        NORMAL,WARNING,ERROR,DEBUG,INFO
    }
}
