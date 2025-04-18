package tc.arcadia.timedwings.commands.console;


import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.commands.Command;
import tc.arcadia.timedwings.message.MessageManager;
import tc.arcadia.timedwings.message.MessagePlaceholder;
import tc.arcadia.timedwings.migrate.MigrationManager;

public class MigrateCommand extends Command {

    public MigrateCommand(TimedWings plugin) {
        super(plugin);
    }

    @Override
    public boolean requiresPermission() {
        return true;
    }

    @Override
    public void onPlayerCommand(Player player, String[] args) {
        MessageManager messageManager = plugin.getMessageManager();
        messageManager.sendLanguageMessage(player, "Commands.Migrate.Console-Only");
    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender sender, String[] args) {
        MessageManager messageManager = plugin.getMessageManager();
        MigrationManager migrationManager = plugin.getMigrationManager();
        if(args.length == 0){
            if(migrationManager.getAvailableMigrators().isEmpty()){
                messageManager.sendLanguageMessage(sender, "Commands.Migrate.No-Migrations-Available");
            }else{
                String availableMigrators = String.join(",",migrationManager.getAvailableMigrators());
                messageManager.sendLanguageMessage(sender, "Commands.Migrate.Usage",
                        new MessagePlaceholder().add("migrations", availableMigrators));
            }
        }else{
            String pluginName = args[0].toLowerCase();
            boolean migrated = migrationManager.migrateFrom(pluginName);
            if(migrated){
                messageManager.sendLanguageMessage(sender, "Commands.Migrate.Success", new MessagePlaceholder().add("plugin", args[0]));
            }else{
                messageManager.sendLanguageMessage(sender, "Commands.Migrate.Failure", new MessagePlaceholder().add("plugin", args[0]));
            }
        }
    }

    @Override
    public String getName() {
        return "migrate";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}
