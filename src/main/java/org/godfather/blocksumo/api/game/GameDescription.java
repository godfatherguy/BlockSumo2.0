package org.godfather.blocksumo.api.game;

import org.bukkit.Material;

import java.util.List;

public interface GameDescription {

    String getMinigameName();

    List<String> getMinigameDescription();

    Material getGUIIcon();
}
