package rownox.net.rowevents;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;
import rownox.net.rowevents.files.EventConfig;
import rownox.net.rowevents.players.PlayerWrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class RowEvents extends JavaPlugin {

    private File eventConfigFile;
    public static FileConfiguration eventConfig;


    @Override
    public void onEnable() {
        initConfig(eventConfig, eventConfigFile, "events.yml");
        EventConfig.initEventConfigs(eventConfig);
    }

    @Override
    public void onDisable() {
        saveConfig(eventConfig, eventConfigFile);
    }

    private void initConfig(FileConfiguration config, File file, String path) {
        saveDefaultConfig();

        file = new File(getDataFolder(), path);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig(FileConfiguration config, File file) {
        if (config != null) {
            try {
                config.save(file);
            } catch (IOException e) {
                getLogger().severe(e.toString());
            }
        }
    }
}
