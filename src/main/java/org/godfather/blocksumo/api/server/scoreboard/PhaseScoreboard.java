package org.godfather.blocksumo.api.server.scoreboard;

import org.godfather.blocksumo.api.Bootstrap;

public abstract class PhaseScoreboard implements Scoreboard {

    protected final Bootstrap bootstrap;

    protected PhaseScoreboard(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }
}
