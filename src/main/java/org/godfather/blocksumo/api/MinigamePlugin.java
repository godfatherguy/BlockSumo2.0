package org.godfather.blocksumo.api;

import org.bukkit.plugin.java.JavaPlugin;
import org.godfather.blocksumo.BlockSumo;

public abstract class MinigamePlugin extends JavaPlugin {

    private final BlockSumo minigame;

    protected MinigamePlugin(BlockSumo minigame) {
        this.minigame = minigame;
    }

    @Override
    public void onLoad() {
        minigame.setPlugin(this);
    }

    @Override
    public void onEnable() {
        minigame.load(this);
    }

    @Override
    public void onDisable() {
        if (minigame.isLoaded())
            minigame.unload();
    }
}
