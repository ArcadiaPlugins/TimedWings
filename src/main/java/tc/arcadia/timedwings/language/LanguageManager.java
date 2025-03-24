package tc.arcadia.timedwings.language;

import tc.arcadia.timedwings.TimedWings;
import tc.arcadia.timedwings.manager.Manager;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LanguageManager extends Manager {
    private final Map<String,FileConfiguration> languages;
    private final String defaultLanguage;
    private final boolean autoDetectLanguage;
    public LanguageManager(TimedWings plugin){
        super(plugin);

        languages = new HashMap<>();

        if(plugin.getConfiguration().getBoolean("general.language.use-client-language.status")){
            defaultLanguage = plugin.getConfiguration().getString("general.language.use-client-language.fallback-language");
            autoDetectLanguage = true;
            plugin.logger().info("Automatically language detection &aenabled");
        }else{
            defaultLanguage = plugin.getConfiguration().getString("general.language.default");
            autoDetectLanguage = false;
            plugin.logger().info("Automatically language detection &cdisabled");
        }
        File file = new File(plugin.getDataFolder(), "languages");
        if(!file.exists()) file.mkdirs();
        for(File languageFile : file.listFiles()){
            plugin.logger().info("Loading language file: "+languageFile.getName().replace(".yml", ""));
            FileConfiguration config = YamlConfiguration.loadConfiguration(languageFile);
            try {
                languages.put(languageFile.getName().replace(".yml", ""), config);
            }catch (Exception e){
                plugin.logger().error("Error while loading language file: "+languageFile.getName());
            }
        }
    }

    public FileConfiguration get(){
        return get(null);
    }
    public FileConfiguration get(CommandSender sender){
        if(sender instanceof Player) return get((Player) sender);
        return get(null);
    }
    public FileConfiguration get(Player player){
        String language = defaultLanguage;
        if(player != null && autoDetectLanguage){
            if(languages.containsKey(player.getLocale())) language = player.getLocale();
        }
        return languages.get(language);
    }


}
