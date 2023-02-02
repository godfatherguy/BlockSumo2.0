package org.godfather.blocksumo.api.utils;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;

@SuppressWarnings("unused")
public final class WorldUtils {

    public static float getNearestYaw(float yaw) {
        if (yaw >= -22.5 && yaw < 22.5) {
            return 0.0f;
        } else if (yaw >= 22.5 && yaw < 67.5) {
            return 45.0f;
        } else if (yaw >= 67.5 && yaw < 112.5) {
            return 90.0f;
        } else if (yaw >= 112.5 && yaw < 157.5) {
            return 135.0f;
        } else if (yaw >= 157.5 || yaw < -157.5) {
            return 180.0f;
        } else if (yaw >= -157.5 && yaw < -112.5) {
            return -135.0f;
        } else if (yaw >= -112.5 && yaw < -67.5) {
            return -90.0f;
        } else return -45.0f;
    }

    public static double getAdjusted(double coordinate) {
        double rounded = Math.round(coordinate * 10.0) / 10.0;
        int intPart = Integer.parseInt(String.valueOf(rounded).split("\\.")[0]);
        double decimalPart = Integer.parseInt(String.valueOf(rounded).split("\\.")[1]) * 0.1;

        if (decimalPart >= 0 && decimalPart <= 0.2)
            return intPart;

        else if (decimalPart > 0.2 && decimalPart < 0.8)
            if (intPart < 0)
                return intPart - 0.5;
            else return intPart + 0.5;

        else if (decimalPart >= 0.8)
            if (intPart < 0)
                return intPart - 1;
            else return intPart + 1;

        else if (intPart < 0)
            return intPart - 0.5;
        else return intPart + 0.5;
    }

    public static List<Block> getBlocksAbove(Location block) {
        List<Block> blocksAbove = Lists.newArrayList();

        for(int y = 1; y < 256; y++) {
            Location loc = block.add(0, y, 0);

            if(loc.getBlock().getType() == Material.AIR) continue;
            blocksAbove.add(loc.getBlock());
        }
        return blocksAbove;
    }

    public static Location getRandomLocation(Location start, double radius, double radiusY) {
        List<Location> possibleLocations = Lists.newArrayList();

        for (double x = -radius / 2; x < radius / 2; x++) {

            for (double z = -radius / 2; z < radius / 2; z++) {

                for (double y = -radiusY / 2; y < radiusY / 2; y++) {
                    Location loc = start.add(x, y, z);

                    if(loc.getBlock().getType() != Material.AIR) continue;
                    if(getBlocksAbove(loc).size() > 0) continue;
                    possibleLocations.add(loc);
                }
            }
        }

        return Utils.getRandomInList(possibleLocations);
    }
}
