package org.godfather.blocksumo.api.items;

import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.Bootstrap;

public interface Interactable {

    void interact(Player player, Bootstrap bootstrap);

    ItemBuilder getBuilder();
}
