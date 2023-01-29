package org.godfather.blocksumo.api.config;

import org.bukkit.configuration.file.FileConfiguration;

public interface Config {

    void create();

    void save();

    void reload();

    FileConfiguration getConfig();
}
