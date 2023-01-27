package org.godfather.blocksumo.api;

import org.bukkit.Bukkit;
import org.bukkit.event.EventPriority;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.godfather.blocksumo.api.game.GameDescription;
import org.godfather.blocksumo.api.game.GameManager;
import org.godfather.blocksumo.api.server.events.FastEvent;
import org.godfather.blocksumo.api.server.events.FastEventsManager;
import org.godfather.blocksumo.api.server.events.ServerFastEvent;

import java.util.Optional;
import java.util.logging.Logger;

public abstract class Bootstrap {

    public static Logger LOGGER = Bukkit.getLogger();
    private JavaPlugin plugin;
    private boolean loaded = false;
    private FastEventsManager fastEventsManager;
    private GameManager gameManager;
    private GameDescription description;

    protected abstract void onLoad();

    protected abstract void onReload();

    protected abstract void onUnload();

    public final void load(JavaPlugin plugin) {
        this.plugin = plugin;

        fastEventsManager = new FastEventsManager(this);
        fastEventsManager.register();

        registerVariable(ServerFastEvent.builder(WorldLoadEvent.class)
                .consumer(event -> {
                    if (event.getWorld() != null) {
                        event.getWorld().setAutoSave(false);
                        event.getWorld().setTime(1000);
                        event.getWorld().setGameRuleValue("doDaylightCycle", "false");
                    }
                }).priority(EventPriority.MONITOR).build());

        loaded = true;
        onLoad();
    }

    public final void reload() {
        onReload();
    }

    public final void unload() {
        fastEventsManager.unregister();
        gameManager.unload();
        loaded = false;
        onUnload();
    }

    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public FastEventsManager getFastEventsManager() {
        return fastEventsManager;
    }

    public void registerVariable(FastEvent<?> fastEvent) {
        fastEventsManager.registerEvent(fastEvent);
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
        gameManager.load();
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public Optional<GameDescription> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(GameDescription description) {
        this.description = description;
    }
}
