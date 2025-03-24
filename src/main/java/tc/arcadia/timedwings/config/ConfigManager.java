package tc.arcadia.timedwings.config;

import tc.arcadia.timedwings.TimedWings;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigManager {

    private final TimedWings plugin;
    private final Map<String, ConfigFile> loadedConfigs = new HashMap<>();


    public ConfigManager(TimedWings plugin){
        this.plugin = plugin;

        loadConfigFiles();
    }

    public void loadConfigFiles(){
        if(!this.plugin.getDataFolder().exists()) this.plugin.getDataFolder().mkdir();


        Map<String, File> configFiles = new LinkedHashMap<>();
        configFiles.put("config.yml", new File(this.plugin.getDataFolder(), "config.yml"));

        File languageFolder = new File(plugin.getDataFolder(), "languages");
        if(!languageFolder.exists()) languageFolder.mkdirs();
        configFiles.put("languages/tr_tr.yml", new File(this.plugin.getDataFolder()+"/languages", "tr_tr.yml"));
        configFiles.put("languages/en_us.yml", new File(this.plugin.getDataFolder()+"/languages", "en_us.yml"));


        for (Map.Entry<String, File> configEntry : configFiles.entrySet()) {
            String fileName = configEntry.getKey();
            File configFile = configEntry.getValue();
            if(!configFile.exists()){
                try {
                    configFile.createNewFile();
                    try(InputStream inputStream = this.plugin.getResource(fileName); OutputStream outputStream = Files.newOutputStream(configFile.toPath())){
                        if(inputStream != null){
                            ByteStreams.copy(inputStream,outputStream);
                        }
                    }
                }catch (IOException exception){
                    Bukkit.getLogger().info("TimedWings | Error: An error occurred while creating the "+fileName+" file. Please back up the plugin folder, delete it and try again. If the problem continues, please contact the developer.");
                }
            }
        }

    }
    public InputStream getConfigContent(File configFile) {
        if (!configFile.exists()) {
            return null;
        }

        try {
            return getConfigContent(new FileReader(configFile));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }
    public InputStream getConfigContent(Reader reader) {
        try {
            String addLine, currentLine, pluginName = this.plugin.getDescription().getName();
            int commentNum = 0;

            StringBuilder whole = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(reader);

            while ((currentLine = bufferedReader.readLine()) != null) {
                if (currentLine.contains("#")) {
                    addLine = currentLine.replace("[!]", "IMPORTANT").replace(":", "-").replaceFirst("#", pluginName + "_COMMENT_" + commentNum + ":");
                    whole.append(addLine).append("\n");
                    commentNum++;
                } else {
                    whole.append(currentLine).append("\n");
                }
            }
            String config = whole.toString();
            InputStream configStream = new ByteArrayInputStream(config.getBytes(StandardCharsets.UTF_8));
            bufferedReader.close();
            return configStream;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public ConfigFile getConfig(File configPath) {
        ConfigFile cached = this.loadedConfigs.get(configPath.getPath());

        if (cached != null) {
            return cached;
        }
        ConfigFile config = new ConfigFile(this, configPath);
        this.loadedConfigs.put(configPath.getPath(), config);

        return config;
    }

    public boolean isFileExist(File filePath) {
        return filePath.exists();
    }

    public void onDisable(){

    }


}
