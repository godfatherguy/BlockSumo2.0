package org.godfather.blocksumo.bukkit.manager;

import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.config.ConfigManager;

public final class BlockSumoConfigManager extends ConfigManager {

    public BlockSumoConfigManager(Bootstrap bootstrap) {
        super(bootstrap);
    }

    @Override
    protected void onLoad() {
        createConfig("maps.yml");
    }

    @Override
    protected void onUnload() {
        clearConfigs();
    }
}
