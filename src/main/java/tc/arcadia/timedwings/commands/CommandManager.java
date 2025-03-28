package tc.arcadia.timedwings.commands;

import tc.arcadia.timedwings.commands.player.*;
import tc.arcadia.timedwings.manager.Manager;
import tc.arcadia.timedwings.TimedWings;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends Manager implements CommandExecutor, TabCompleter {

    private List<Command> playerCommands;
    public CommandManager(TimedWings plugin) {
        super(plugin);

        playerCommands = new ArrayList<>();
        PluginCommand pluginCommand = plugin.getCommand("timedwings");
        if(pluginCommand == null) return;

        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);

        registerCommands(
            new CheckCommand(plugin),
            new ClearCommand(plugin),
            new DefaultCommand(plugin),
            new GiveCommand(plugin),
            new SetCommand(plugin)
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
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {

        Player player = null;
        Command executedCommand = null;

        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if(args.length == 0){
            executedCommand = playerCommands.stream()
                    .filter(c -> c.getName().equalsIgnoreCase("default"))
                    .findFirst()
                    .orElse(null);
        }else{
            executedCommand = playerCommands.stream()
                    .filter(c -> c.getName().equalsIgnoreCase(args[0]))
                    .findFirst()
                    .orElse(null);
        }

        if(executedCommand == null) return false;

        if(executedCommand.requiresPermission() && !sender.hasPermission("timedwings.command."+executedCommand.getName())) return false;

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
