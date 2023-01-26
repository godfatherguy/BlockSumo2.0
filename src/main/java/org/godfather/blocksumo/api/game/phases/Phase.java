package org.godfather.blocksumo.api.game.phases;

import org.bukkit.scoreboard.Scoreboard;

import java.util.Optional;

public interface Phase {

    void onLoad();

    void onUnload();

    void setNextPhase(Phase phase);

    void setPreviousPhase(Phase phase);

    Optional<Phase> getNextPhase();

    Optional<Phase> getPreviousPhase();

    Scoreboard getScoreboard();
}
