package org.godfather.blocksumo.api.game.phases;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.Game;
import org.godfather.blocksumo.api.server.scoreboard.Scoreboard;

import java.util.Optional;

public abstract class GamePhase implements Listener, Phase {

    protected Game parentGame;
    protected final Bootstrap bootstrap;
    protected Phase previousPhase;
    protected Phase nextPhase;
    protected boolean running = false;
    private Scoreboard scoreboard;

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

    public abstract void onLoad();

    public abstract void onUnload();

    public final void start() {
        running = true;
        setParentGame(bootstrap.getGameManager().getRunningGame());

        bootstrap.getPlugin().getServer().getPluginManager().registerEvents(this, bootstrap.getPlugin());

        onLoad();

        bootstrap.getScoreboardManager().startScoreboard(scoreboard);
    }

    public final void end() {
        running = false;
        HandlerList.unregisterAll(this);
    }

    public boolean isRunning() {
        return running;
    }

    public void setParentGame(Game game) {
        parentGame = game;
    }

    @Override
    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }
}
