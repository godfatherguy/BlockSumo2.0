package org.godfather.blocksumo.api.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class MinigameConfig implements Config {

    private final JavaPlugin plugin;
    private final String name;
    private final File directory;
    private File customFile;
    private FileConfiguration customConfiguration;

    public MinigameConfig(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        directory = plugin.getDataFolder();
    }

    @Override
    public void create() {
        customFile = new File(directory, name + ".yml");
        if (!customFile.exists()) {

            boolean mkdir = customFile.getParentFile().mkdirs();
            plugin.saveResource(name + ".yml", false);
        }

        customConfiguration = YamlConfiguration.loadConfiguration(customFile);
    }

    @Override
    public void save() {
        try {
            customConfiguration.save(customFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reload() {
        customConfiguration = YamlConfiguration.loadConfiguration(customFile);
    }

    @Override
    public FileConfiguration getConfig() {
        return customConfiguration;
    }
}
