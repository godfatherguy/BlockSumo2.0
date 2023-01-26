package org.godfather.blocksumo.api.game;

import org.bukkit.Bukkit;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.phases.GamePhase;
import org.godfather.blocksumo.api.server.scoreboard.Scoreboard;

import java.util.UUID;

public class Game {

    protected final Bootstrap bootstrap;
    protected final UUID uuid;
    private GamePhase actualPhase;

    public Game(Bootstrap bootstrap, GamePhase gamePhase) {
        this.bootstrap = bootstrap;
        uuid = UUID.randomUUID();
        actualPhase = gamePhase;
    }

    public void nextPhase() {
        actualPhase.end();

        if (actualPhase.getNextPhase().isEmpty()) {
            Bukkit.shutdown();
            return;
        }

        this.actualPhase = (GamePhase) actualPhase.getNextPhase().get();
        actualPhase.start();
    }

    public GamePhase getActualPhase() {
        return actualPhase;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Scoreboard getScoreboard() {
        return actualPhase.getScoreboard();
    }
}
