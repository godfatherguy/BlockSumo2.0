package org.godfather.blocksumo.bukkit;

import org.bukkit.Material;
import org.godfather.blocksumo.api.game.GameDescription;

import java.util.List;

public class BlockSumoDescription implements GameDescription {


    @Override
    public String getMinigameName() {
        return "Block Sumo";
    }

    @Override
    public List<String> getMinigameDescription() {
        return null;
    }

    @Override
    public Material getGUIIcon() {
        return Material.SHEARS;
    }
}
