package org.godfather.blocksumo.api.server.manager;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.godfather.blocksumo.api.Bootstrap;

public abstract class Manager implements Listener {

    protected final Bootstrap minigame;

    protected Manager(Bootstrap minigame) {
        this.minigame = minigame;
    }

    protected abstract void onLoad();

    protected abstract void onUnload();

    public final void load() {
        minigame.getPlugin().getServer().getPluginManager().registerEvents(this, minigame.getPlugin());
        onLoad();
    }

    public final void unload() {
        HandlerList.unregisterAll(this);
        onUnload();
    }

    public Bootstrap getBootstrap() {
        return minigame;
    }
}
