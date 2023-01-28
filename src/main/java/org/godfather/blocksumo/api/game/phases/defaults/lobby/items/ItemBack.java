package org.godfather.blocksumo.api.game.phases.defaults.lobby.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.items.Interactable;
import org.godfather.blocksumo.api.items.ItemBuilder;

public class ItemBack implements Interactable {

    private final ItemBuilder builder;

    public ItemBack() {
        builder = new ItemBuilder(Material.BED).setName("§cTorna alla lobby");
    }

    @Override
    public void interact(Player player, Bootstrap bootstrap) {
        player.kickPlayer("Sei uscito dalla partita.");
    }

    @Override
    public ItemBuilder getBuilder() {
        return builder;
    }
}
