package org.godfather.blocksumo;

import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.bukkit.manager.BlockSumoGameManager;

public class BlockSumoBootstrap extends Bootstrap {

    @Override
    protected void onLoad() {
        setGameManager(new BlockSumoGameManager(this));
    }

    @Override
    protected void onReload() {

    }

    @Override
    protected void onUnload() {

    }
}
