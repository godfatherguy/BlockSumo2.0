package org.godfather.blocksumo.api.game.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.items.Interactable;

public class InteractableInteractEvent extends Event implements Cancellable {

    private final Bootstrap bootstrap;
    private final Player interactor;
    private final Interactable interactable;
    private boolean cancelled = false;
    private static final HandlerList handlers = new HandlerList();

    public InteractableInteractEvent(Bootstrap bootstrap, Player player, Interactable interactable) {
        this.bootstrap = bootstrap;
        this.interactor = player;
        this.interactable = interactable;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    public Player getPlayer() {
        return interactor;
    }

    public Interactable getInteractable() {
        return interactable;
    }
}
