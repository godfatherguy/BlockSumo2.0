package org.godfather.blocksumo.api.game.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.server.manager.Manager;

import java.util.HashMap;
import java.util.Map;

public abstract class PlayerManager<T extends GamePlayer> extends Manager {

    private final Class<T> playerClass;
    private final Map<Player, T> gamePlayers = new HashMap<>();

    public PlayerManager(Bootstrap bootstrap, Class<T> playerClass) {
        super(bootstrap);
        this.playerClass = playerClass;
    }

    public T getProfile(Player player) {
        return gamePlayers.get(player);
    }

    private void registerPlayer(Player player) throws Exception {
        gamePlayers.put(player, playerClass.getDeclaredConstructor().newInstance());
    }

    public void unregisterPlayer(Player player) {
        gamePlayers.remove(player);
    }

    protected void registerAll() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            try {
                registerPlayer(player);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    protected void unregisterAll() {
        Bukkit.getOnlinePlayers().forEach(this::unregisterPlayer);
        gamePlayers.clear();
    }
}
