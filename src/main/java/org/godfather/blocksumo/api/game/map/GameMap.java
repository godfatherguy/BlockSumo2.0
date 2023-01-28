package org.godfather.blocksumo.api.game.map;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Optional;

public interface GameMap {

    String getName();

    World getWorld();

    Optional<Location> getLocation(String name);

    Location getSpawn();
}
