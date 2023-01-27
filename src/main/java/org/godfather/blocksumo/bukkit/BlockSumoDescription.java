package org.godfather.blocksumo.bukkit;

import com.google.common.collect.Lists;
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
        List<String> description = Lists.newArrayList();
        description.add("Muoviti tra le piattaforme, spingi i tuoi nemici,");
        description.add("sopravvivi ai cumuli di blocchi e guadagnati");
        description.add("il titolo di miglior Rikishi della storia!");
        description.add("Se perdi tutte le tue vite verrai eliminato.");
        return description;
    }

    @Override
    public Material getGUIIcon() {
        return Material.SHEARS;
    }
}
