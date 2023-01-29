package org.godfather.blocksumo.api.config;

import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.server.manager.Manager;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ConfigManager extends Manager {

    private final Map<String, Config> configs = new ConcurrentHashMap<>();

    protected ConfigManager(Bootstrap bootstrap) {
        super(bootstrap);
    }

    protected void createConfig(String name) {
        Config config = new MinigameConfig(bootstrap.getPlugin(), name);
        configs.put(name, config);
        config.create();
        config.save();
        config.reload();
        Bootstrap.LOGGER.info("§eConfiguration " + name + " loaded!");
    }

    public void removeConfig(String name) {
        configs.remove(name);
    }

    public void clearConfigs() {
        configs.clear();
    }

    public Optional<Config> getConfig(String name) {
        return Optional.of(configs.get(name));
    }
}
