package org.godfather.blocksumo.api.game;

import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.server.manager.Manager;

public abstract class GameManager extends Manager {

    private Game runningGame;

    protected GameManager(Bootstrap bootstrap) {
        super(bootstrap);
    }

    public Game getRunningGame() {
        return runningGame;
    }
}
