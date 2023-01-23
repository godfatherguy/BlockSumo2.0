package org.godfather.blocksumo.api.server.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class FastEventsManager implements Listener {

    private final JavaPlugin plugin;
    private final Map<Class<? extends Event>, FastEvent<?>> fastEvents = new HashMap<>();

    public FastEventsManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public FastEvent<?> registerEvent(FastEvent<?> fastEvent) {
        FastEvent<Event> generic = (FastEvent<Event>) fastEvent;

        if (fastEvents.containsKey(fastEvent.getEventClass())) {
            HandlerList.unregisterAll(fastEvent);
        }
        EventExecutor executor = (((listener, event) -> {
            if (event.getClass().equals(fastEvent.getEventClass())) {
                generic.accept(event);
            }
        }));

        plugin.getServer().getPluginManager().registerEvent(fastEvent.getEventClass(),
                fastEvent, fastEvent.priority(), executor, plugin, false);

        return fastEvents.put(fastEvent.getEventClass(), fastEvent);
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void unregister() {
        fastEvents.values().forEach(HandlerList::unregisterAll);
        HandlerList.unregisterAll(this);
    }
}
