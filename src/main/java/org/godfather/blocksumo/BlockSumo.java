package org.godfather.blocksumo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.godfather.blocksumo.api.server.ServerPhase;
import org.godfather.blocksumo.api.server.events.FastEventsManager;

import java.util.logging.Logger;

public class BlockSumo {

    public static Logger LOGGER = Bukkit.getLogger();
    private JavaPlugin plugin;
    private ServerPhase phase = ServerPhase.LOAD;
    private boolean loaded = false;
    private FastEventsManager fastEventsManager;

    public final void load(JavaPlugin plugin) {
        this.plugin = plugin;

        fastEventsManager = new FastEventsManager(plugin);
        fastEventsManager.register();

        loaded = true;
    }

    public final void unload() {
        fastEventsManager.unregister();
        loaded = false;
    }

    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void nextPhase() {
        if (phase.getNextPhase().isEmpty())
            return;

        phase = phase.getNextPhase().get();
    }

    public ServerPhase getPhase() {
        return phase;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public FastEventsManager getFastEventsManager() {
        return fastEventsManager;
    }
}
