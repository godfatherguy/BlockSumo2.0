package org.godfather.blocksumo.api;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class MinigamePlugin extends JavaPlugin {

    private final Bootstrap minigame;

    protected MinigamePlugin(Bootstrap minigame) {
        this.minigame = minigame;
    }

    @Override
    public void onLoad() {
        minigame.setPlugin(this);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        minigame.load(this);
    }

    @Override
    public void onDisable() {
        if (minigame.isLoaded())
            minigame.unload();
    }
}
