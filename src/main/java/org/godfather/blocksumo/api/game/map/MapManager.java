package org.godfather.blocksumo.api.game.map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.server.manager.Manager;
import org.godfather.blocksumo.api.utils.Utils;

import java.io.File;
import java.util.Arrays;

@SuppressWarnings("unused")
public final class MapManager extends Manager {

    private GameMap map = null;

    public MapManager(Bootstrap bootstrap) {
        super(bootstrap);
    }

    @Override
    protected void onLoad() {
        File path = new File(bootstrap.getPlugin().getDataFolder(), "gameMaps");
        String[] directories = path.list((dir, name) -> new File(dir, name).isDirectory());

        if(directories == null || directories.length < 1) {
            Bootstrap.LOGGER.warning("§c[!] Nessuna mappa trovata nella cartella gameMaps.");
            return;
        }
        File mapFolder = new File(Utils.getRandomInList(Arrays.stream(directories).toList()));

        loadMap(mapFolder.getName());
    }

    @Override
    protected void onUnload() {
        if (map.isLoaded()) {
            map.unload();
            map = null;
        }

        for (World world : Bukkit.getWorlds()) {
            File[] files = new File(world.getWorldFolder().getAbsolutePath() + "/playerdata/").listFiles();
            if (files == null)
                return;
            for (File file : files) {
                boolean delete = file.delete();
            }
        }
    }

    private void loadMap(String name) {
        File gameMapsFolder = new File(bootstrap.getPlugin().getDataFolder(), "gameMaps");

        if (!gameMapsFolder.exists()) {
            boolean mkdir = gameMapsFolder.mkdirs();
        }

        map = new MinigameMap(gameMapsFolder, name, true);
        Bootstrap.LOGGER.info("§eMap successfully loaded! (" + name + ")");
    }

    public GameMap getMap() {
        return map;
    }
}
