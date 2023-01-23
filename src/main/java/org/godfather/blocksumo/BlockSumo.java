package org.godfather.blocksumo;

import org.bukkit.Bukkit;
import org.bukkit.event.EventPriority;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.godfather.blocksumo.api.server.ServerPhase;
import org.godfather.blocksumo.api.server.events.FastEvent;
import org.godfather.blocksumo.api.server.events.FastEventsManager;
import org.godfather.blocksumo.api.server.events.ServerFastEvent;

import java.util.logging.Logger;

public class BlockSumo {

    public static Logger LOGGER = Bukkit.getLogger();
    private JavaPlugin plugin;
    private ServerPhase phase = ServerPhase.LOAD;
    private boolean loaded = false;
    private FastEventsManager fastEventsManager;

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
    }

    public final void reload() {
        //todo
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
}
