package org.godfather.blocksumo.api.game.phases;

import org.bukkit.event.Listener;
import org.godfather.blocksumo.api.Bootstrap;

import java.util.Optional;

public abstract class GamePhase implements Listener, Phase {

    protected final Bootstrap bootstrap;
    private Phase previousPhase;
    protected Phase nextPhase;
    protected boolean running = false;

    public GamePhase(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    public void setNextPhase(Phase phase) {
        nextPhase = phase;
        nextPhase.setPreviousPhase(this);
    }

    public void setPreviousPhase(Phase phase) {
        previousPhase = phase;
    }

    public Optional<Phase> getNextPhase() {
        return Optional.ofNullable(nextPhase);
    }

    public Optional<Phase> getPreviousPhase() {
        return Optional.ofNullable(previousPhase);
    }

    public final void onLoad() {
        running = true;
    }

    public final void onUnload() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
