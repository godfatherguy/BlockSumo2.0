package org.godfather.blocksumo.api.game.phases;

import org.godfather.blocksumo.api.server.scoreboard.Scoreboard;
import org.godfather.blocksumo.api.server.tablist.Tablist;

import java.util.Optional;

public interface Phase {

    void start();

    void end();

    void setNextPhase(Phase phase);

    void setPreviousPhase(Phase phase);

    Optional<Phase> getNextPhase();

    Optional<Phase> getPreviousPhase();

    void setScoreboard(Scoreboard scoreboard);

    void setTablist(Tablist tablist);

    boolean joinEnabled();
}
