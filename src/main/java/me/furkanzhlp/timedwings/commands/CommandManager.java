package me.furkanzhlp.timedwings.commands;

import com.craftaro.skyblock.command.SubCommand;
import me.furkanzhlp.timedwings.TimedWings;
import me.furkanzhlp.timedwings.commands.player.GiveTimeCommand;
import me.furkanzhlp.timedwings.manager.Manager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends Manager implements CommandExecutor, TabCompleter {

    private List<Command> playerCommands;
    private List<Command> adminCommands;
    public CommandManager(TimedWings plugin) {
        super(plugin);

        playerCommands = new ArrayList<>();
        PluginCommand pluginCommand = plugin.getCommand("timedwings");
        if(pluginCommand == null) return;

        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);

        registerCommands(
            new GiveTimeCommand(plugin)
        );
    }
    public void registerCommand(Command command) {
        boolean exists = playerCommands.stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(command.getName()));
        if (!exists) {
            playerCommands.add(command);
        }
    }
    public void registerCommands(Command... commands) {
        for (Command command : commands) {
            registerCommand(command);
        }
    }
    public void registerAdminCommand(Command command) {
        boolean exists = playerCommands.stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(command.getName()));
        if (!exists) {
            adminCommands.add(command);
        }
    }
    public void registerAdminCommands(Command... commands) {
        for (Command command : commands) {
            registerAdminCommand(command);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {

        Player player = null;
        Command executedCommand = null;
        boolean requiredPermission = false;

        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if(args.length == 0){
            executedCommand = playerCommands.stream()
                    .filter(c -> c.getName().equalsIgnoreCase("default"))
                    .findFirst()
                    .orElse(null);
        }else{
            if(args[0].equalsIgnoreCase("admin")){
                requiredPermission = true;
                if(args.length == 1){
                    executedCommand = adminCommands.stream()
                            .filter(c -> c.getName().equalsIgnoreCase("default"))
                            .findFirst()
                            .orElse(null);
                }else{
                    executedCommand = adminCommands.stream()
                            .filter(c -> c.getName().equalsIgnoreCase(args[1]))
                            .findFirst()
                            .orElse(null);
                }
            }else{
                executedCommand = playerCommands.stream()
                        .filter(c -> c.getName().equalsIgnoreCase(args[0]))
                        .findFirst()
                        .orElse(null);
            }
        }

        if(executedCommand == null) return false;

        if(requiredPermission && !sender.hasPermission("timedwings.command."+executedCommand.getName())) return false;

        if (player != null) {
            executedCommand.onPlayerCommand(player, args);
        } else {
            executedCommand.onConsoleCommand((ConsoleCommandSender) sender, args);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
