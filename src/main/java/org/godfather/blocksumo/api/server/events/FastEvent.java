package org.godfather.blocksumo.api.server.events;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

public interface FastEvent<T extends Event> extends Consumer<T>, Listener {

    Class<T> getEventClass();

    EventPriority priority();
}
