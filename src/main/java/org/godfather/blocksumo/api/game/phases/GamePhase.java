package org.godfather.blocksumo.api.game.phases;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.Game;

import java.util.Optional;

public abstract class GamePhase implements Listener, Phase {

    protected Game parentGame;
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

    protected abstract void onLoad();

    protected abstract void onUnload();

    public final void start() {
        running = true;
        setParentGame(bootstrap.getGameManager().getRunningGame());

        bootstrap.getPlugin().getServer().getPluginManager().registerEvents(this, bootstrap.getPlugin());

        onLoad();
    }

    public final void end() {
        running = false;
        HandlerList.unregisterAll(this);

        onUnload();
    }

    public boolean isRunning() {
        return running;
    }

    public void setParentGame(Game game) {
        parentGame = game;
    }
}
