package org.godfather.blocksumo.api.game;

import org.bukkit.Bukkit;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.phases.GamePhase;

import java.util.UUID;

public class Game {

    protected final Bootstrap bootstrap;
    protected final UUID uuid;
    private GamePhase actualPhase;

    public Game(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
        uuid = UUID.randomUUID();
    }

    public void nextPhase() {
        actualPhase.onUnload();

        if (actualPhase.getNextPhase().isEmpty()) {
            Bukkit.shutdown();
            return;
        }

        this.actualPhase = (GamePhase) actualPhase.getNextPhase().get();
        actualPhase.onLoad();
    }

    public GamePhase getActualPhase() {
        return actualPhase;
    }

    public UUID getUUID() {
        return uuid;
    }
}
