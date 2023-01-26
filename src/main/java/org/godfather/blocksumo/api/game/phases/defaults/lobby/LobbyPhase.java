package org.godfather.blocksumo.api.game.phases.defaults.lobby;

import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.phases.GamePhase;
import org.godfather.blocksumo.api.server.scoreboard.Scoreboard;

public final class LobbyPhase extends GamePhase {

    public LobbyPhase(Bootstrap bootstrap) {
        super(bootstrap);
    }

    @Override
    public void onLoad() {

    }

    @Override
    protected void onUnload() {

    }

    @Override
    public Scoreboard getScoreboard() {
        return null;
    }

    @Override
    public boolean joinEnabled() {
        return true;
    }
}
