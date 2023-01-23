package org.godfather.blocksumo.api.server.events;

import org.bukkit.event.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.EventExecutor;
import org.godfather.blocksumo.BlockSumo;
import org.godfather.blocksumo.api.server.ServerPhase;

import java.util.HashMap;
import java.util.Map;

public final class FastEventsManager implements Listener {

    private final BlockSumo minigame;
    private final Map<Class<? extends Event>, FastEvent<?>> fastEvents = new HashMap<>();

    public FastEventsManager(BlockSumo minigame) {
        this.minigame = minigame;
    }

    public void registerEvent(FastEvent<?> fastEvent) {
        FastEvent<Event> generic = (FastEvent<Event>) fastEvent;

        if (fastEvents.containsKey(fastEvent.getEventClass())) {
            HandlerList.unregisterAll(fastEvent);
        }
        EventExecutor executor = (((listener, event) -> {
            if (event.getClass().equals(fastEvent.getEventClass())) {
                generic.accept(event);
            }
        }));

        minigame.getPlugin().getServer().getPluginManager().registerEvent(fastEvent.getEventClass(),
                fastEvent, fastEvent.priority(), executor, minigame.getPlugin(), false);

        fastEvents.put(fastEvent.getEventClass(), fastEvent);
    }

    public void register() {
        minigame.getPlugin().getServer().getPluginManager().registerEvents(this, minigame.getPlugin());
    }

    public void unregister() {
        fastEvents.values().forEach(HandlerList::unregisterAll);
        HandlerList.unregisterAll(this);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        if (minigame.getPhase() != ServerPhase.END)
            return;

        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, "Il server non è ancora avviato, aspetta...");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }
}
