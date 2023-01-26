package org.godfather.blocksumo.api.game.phases;

import org.godfather.blocksumo.api.server.scoreboard.Scoreboard;

import java.util.Optional;

public interface Phase {

    void start();

    void end();

    void setNextPhase(Phase phase);

    void setPreviousPhase(Phase phase);

    Optional<Phase> getNextPhase();

    Optional<Phase> getPreviousPhase();

    Scoreboard getScoreboard();

    boolean joinEnabled();
}
