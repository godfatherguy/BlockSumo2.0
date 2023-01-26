package org.godfather.blocksumo;

import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.GameManager;
import org.godfather.blocksumo.bukkit.manager.BlockSumoGameManager;

public class BlockSumoBootstrap extends Bootstrap {

    private GameManager gameManager;

    @Override
    protected void onLoad() {
        gameManager = new BlockSumoGameManager(this);
        gameManager.load();
    }

    @Override
    protected void onReload() {

    }

    @Override
    protected void onUnload() {
        gameManager.unload();
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
