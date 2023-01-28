package org.godfather.blocksumo.api.items;

import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class ItemManager {

    private final static Map<String, Interactable> interactables = new ConcurrentHashMap<>();

    public static void registerInteractable(String name, Interactable interactable) {
        if (!interactables.containsKey(name))
            interactables.put(name, interactable);
    }

    public static void unregister() {
        interactables.clear();
    }

    public static Optional<Interactable> getInteractable(ItemStack itemStack) {
        Interactable finalinter = null;
        for (Interactable interactable : interactables.values()) {
            if (!interactable.getBuilder().get().equals(itemStack)) continue;
            finalinter = interactable;
        }
        return Optional.ofNullable(finalinter);
    }

    public static Optional<Interactable> getInteractable(String name) {
        return Optional.ofNullable(interactables.get(name));
    }
}
