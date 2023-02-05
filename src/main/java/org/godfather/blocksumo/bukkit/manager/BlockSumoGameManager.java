package org.godfather.blocksumo.bukkit.manager;

import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.Game;
import org.godfather.blocksumo.api.game.GameManager;
import org.godfather.blocksumo.api.game.phases.defaults.lobby.LobbyPhase;
import org.godfather.blocksumo.bukkit.phases.ingame.IngamePhase;
import org.godfather.blocksumo.bukkit.phases.scoreboards.LobbyScoreboard;
import org.godfather.blocksumo.bukkit.phases.scoreboards.StartingScoreboard;

public final class BlockSumoGameManager extends GameManager {

    public BlockSumoGameManager(Bootstrap bootstrap) {
        super(bootstrap);
    }

    @Override
    protected void onLoad() {
        runningGame = new Game(bootstrap, new LobbyPhase(bootstrap));
        runningGame.getActualPhase().setParentGame(runningGame);
        runningGame.getActualPhase().setScoreboard(new LobbyScoreboard(bootstrap));
        runningGame.getActualPhase().start();
        runningGame.getActualPhase().getNextPhase().ifPresent(phase -> phase.setScoreboard(new StartingScoreboard(bootstrap)));
        runningGame.getActualPhase().getNextPhase().ifPresent(phase -> phase.setNextPhase(new IngamePhase(bootstrap)));
    }

    @Override
    protected void onUnload() {
        runningGame = null;
    }
}
