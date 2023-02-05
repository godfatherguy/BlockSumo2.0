package org.godfather.blocksumo.api.utils;

import org.bukkit.Location;

import java.util.Random;

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

    public static boolean isLocationSafe(Location location) {
        if (!location.getBlock().isEmpty())
            return false;
        if (location.getBlock().isLiquid())
            return false;
        if(location.add(0, -1, 0).getBlock().isEmpty())
            return false;

        return location.add(0, 1, 0).getBlock().isEmpty() && location.add(0, 2, 0).getBlock().isEmpty();
    }

    public static Location getRandomLocation(Location start, double radius, double radiusY) {
        Location randomLocation = null;
        int multiplier;

        do {
            multiplier = new Random().nextInt(2) == 0 ? -1 : 1;
            double x = new Random().nextDouble(radius / 2) * multiplier;

            multiplier = new Random().nextInt(2) == 0 ? -1 : 1;
            double z = new Random().nextDouble(radius / 2) * multiplier;

            Location temporary = start.add(x, 0, z);
            if (temporary.getWorld().getHighestBlockAt(temporary).getY() > start.getY() + radiusY / 2
                    || temporary.getWorld().getHighestBlockAt(temporary).getY() < start.getY() - radiusY / 2)
                continue;

            temporary.setY(temporary.getWorld().getHighestBlockAt(temporary).getY());

            randomLocation = temporary.getBlock().isEmpty() ? temporary : temporary.add(0, 1, 0);

        } while (randomLocation == null || !isLocationSafe(randomLocation));

        randomLocation.setX(getAdjusted(randomLocation.getX()));
        randomLocation.setY(getAdjusted(randomLocation.getY()));
        randomLocation.setZ(getAdjusted(randomLocation.getZ()));

        return randomLocation;
    }
}
