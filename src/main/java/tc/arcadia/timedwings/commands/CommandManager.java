package tc.arcadia.timedwings.commands;

import tc.arcadia.timedwings.commands.console.MigrateCommand;
import tc.arcadia.timedwings.commands.player.*;
import tc.arcadia.timedwings.manager.Manager;
import tc.arcadia.timedwings.TimedWings;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            new SetCommand(plugin),
            new ReloadCommand(plugin),
            new HelpCommand(plugin),
            new VersionCommand(plugin),
            new ToggleCommand(plugin),

            // Migrator
            new MigrateCommand(plugin)
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

        String[] argsWithoutCommand = args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new String[0];

        if (player != null) {
            executedCommand.onPlayerCommand(player, argsWithoutCommand);
        } else {
            executedCommand.onConsoleCommand((ConsoleCommandSender) sender, argsWithoutCommand);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(sender instanceof Player)) return null;

        Player player = (Player) sender;
        boolean hasFullPermission = player.hasPermission("timedwings.*") || player.hasPermission("timedwings.command.*");
        List<String> returnList = new ArrayList<>();

        if(strings.length == 1){
             playerCommands.stream()
                    .filter(c -> c.getName().startsWith(strings[0]) && (hasFullPermission || !c.requiresPermission() || player.hasPermission("timedwings.command."+c.getName())))
                    .map(Command::getName)
                    .forEach(returnList::add);
        }

        returnList.remove("default");
        return returnList;
    }
}
