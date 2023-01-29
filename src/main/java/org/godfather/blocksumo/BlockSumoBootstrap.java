package org.godfather.blocksumo;

import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.bukkit.BlockSumoDescription;
import org.godfather.blocksumo.bukkit.manager.BlockSumoConfigManager;
import org.godfather.blocksumo.bukkit.manager.BlockSumoGameManager;
import org.godfather.blocksumo.bukkit.player.BlockSumoPlayerManager;

public class BlockSumoBootstrap extends Bootstrap {

    @Override
    protected void onLoad() {
        setConfigManager(new BlockSumoConfigManager(this));
        setGameManager(new BlockSumoGameManager(this));
        setDescription(new BlockSumoDescription());
        setPlayerManager(new BlockSumoPlayerManager(this));
    }

    @Override
    protected void onReload() {

    }

    @Override
    protected void onUnload() {

    }
}
