package org.godfather.blocksumo.api.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.map.GameMap;

public final class MapUtils {

    public static Location buildLocation(Bootstrap bootstrap, String path) {
        GameMap map = bootstrap.getMapManager().getMap();
        World world = map != null ? map.getWorld() : Bukkit.getWorld("world");

        if(bootstrap.getConfigManager().getConfig("maps").isEmpty())
            return new Location(world, 0.5, 51, 0.5);

        FileConfiguration config = bootstrap.getConfigManager().getConfig("maps").get().getConfig();

        if(config.get(path) == null)
            return new Location(world, 0.5, 51, 0.5);

        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");
        float yaw = (float) (config.get(path + ".yaw") != null ? config.getDouble(path + ".yaw") : 0.0);
        float pitch = (float) (config.get(path + ".pitch") != null ? config.getDouble(path + ".pitch") : 0.0);

        return new Location(world, x, y, z, yaw, pitch);
    }

    public static Location getLocation(Bootstrap bootstrap, String locName) {
        GameMap map = bootstrap.getMapManager().getMap();
        return buildLocation(bootstrap, map.getName() + "." + locName);
    }
}
