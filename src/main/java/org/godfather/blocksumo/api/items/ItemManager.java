package org.godfather.blocksumo.api.items;

import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class ItemManager {

    private final static Map<ItemBuilder, Interactable> interactables = new ConcurrentHashMap<>();

    public static void registerInteractable(ItemBuilder builder, Interactable interactable) {
        interactables.put(builder, interactable);
    }

    public static void unregister() {
        interactables.clear();
    }

    public static Optional<Interactable> getInteractable(ItemStack itemStack) {
        return Optional.ofNullable((Interactable) interactables.keySet().stream().filter(builder -> builder.get().equals(itemStack)).toArray()[0]);
    }
}
