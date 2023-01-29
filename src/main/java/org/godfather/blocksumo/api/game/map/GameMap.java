package org.godfather.blocksumo.api.game.map;

import org.bukkit.World;

public interface GameMap {

    void load();

    void unload();

    boolean isLoaded();

    World getWorld();

    String getName();
}
