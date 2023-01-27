package org.godfather.blocksumo.bukkit.manager;

import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.Game;
import org.godfather.blocksumo.api.game.GameManager;
import org.godfather.blocksumo.api.game.phases.defaults.lobby.LobbyPhase;

public final class BlockSumoGameManager extends GameManager {

    public BlockSumoGameManager(Bootstrap bootstrap) {
        super(bootstrap);
    }

    @Override
    protected void onLoad() {
        runningGame = new Game(bootstrap, new LobbyPhase(bootstrap));
        runningGame.getActualPhase().setParentGame(runningGame);
        runningGame.getActualPhase().start();
        //todo set next phase
    }

    @Override
    protected void onUnload() {
        runningGame = null;
    }
}
