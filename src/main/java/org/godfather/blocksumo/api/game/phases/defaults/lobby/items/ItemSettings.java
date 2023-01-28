package org.godfather.blocksumo.api.game.phases.defaults.lobby.items;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.items.Interactable;
import org.godfather.blocksumo.api.items.ItemBuilder;
import org.godfather.blocksumo.api.utils.Utils;

public class ItemSettings implements Interactable {

    private final ItemBuilder builder;

    public ItemSettings() {
        builder = new ItemBuilder(Material.NETHER_STAR).setName("§bImpostazioni");
    }

    @Override
    public void interact(Player player, Bootstrap bootstrap) {
        Utils.playSound(player, Sound.DOOR_OPEN, 1, 1F);
        //todo aprire GUI Settings
    }

    @Override
    public ItemBuilder getBuilder() {
        return builder;
    }
}
