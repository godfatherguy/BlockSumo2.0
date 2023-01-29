package org.godfather.blocksumo.api.game.map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.utils.FileUtil;

import java.io.File;
import java.io.IOException;

public class MinigameMap implements GameMap {

    private final File sourceFolder;
    private File actualFolder;
    private World world;

    public MinigameMap(File mapFolder, String mapName, boolean loadOnStart) {
        sourceFolder = new File(mapFolder, mapName);
        if (!loadOnStart)
            return;

        load();
    }

    @Override
    public void load() {
        if (isLoaded())
            return;

        actualFolder = new File(Bukkit.getWorldContainer().getParentFile(), "MAP - " + sourceFolder.getName());
        try {
            FileUtil.copy(sourceFolder, actualFolder);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        world = Bukkit.createWorld(new WorldCreator(actualFolder.getName()));
        if (world != null)
            world.setAutoSave(false);
    }

    @Override
    public void unload() {
        if (world != null)
            Bukkit.unloadWorld(world, false);
        if (actualFolder != null)
            FileUtil.delete(actualFolder);

        world = null;
        actualFolder = null;
        Bootstrap.LOGGER.info("§cMap unloaded...");
    }

    @Override
    public boolean isLoaded() {
        return world != null;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public String getName() {
        return sourceFolder.getName();
    }
}
