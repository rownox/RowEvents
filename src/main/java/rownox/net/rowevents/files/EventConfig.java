package rownox.net.rowevents.files;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class EventConfig {
    public static List<EventConfig> eventConfigList;

    private final String key;
    private final List<String> commandList = new ArrayList<>();
    private final List<String> itemList = new ArrayList<>();

    public EventConfig(String key, FileConfiguration config) {
        this.key = key;
        for (String configKey : config.getKeys(true)) {
            if (configKey.equalsIgnoreCase(key)) {
                commandList.addAll(config.getStringList(key + ".kits.commands"));
                itemList.addAll(config.getStringList(key + ".kits.items"));
            }
        }
    }

    public static void initEventConfigs(FileConfiguration config) {
        for (String key : config.getKeys(true)) {
            eventConfigList.add(new EventConfig(key, config));
        }
    }

    public static EventConfig getConfig(String key) {
        for (EventConfig config : eventConfigList) {
            if (config.getKey().equalsIgnoreCase(key)) {
                return config;
            }
        }
        return null;
    }

}
