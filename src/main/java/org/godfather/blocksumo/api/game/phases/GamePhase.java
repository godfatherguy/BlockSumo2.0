package org.godfather.blocksumo.api.game.phases;

import com.google.common.collect.Lists;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.Game;
import org.godfather.blocksumo.api.server.scoreboard.Scoreboard;

import java.util.List;
import java.util.Optional;

public abstract class GamePhase implements Listener, Phase {

    protected Game parentGame;
    protected final Bootstrap bootstrap;
    protected Phase previousPhase = null;
    protected Phase nextPhase = null;
    protected boolean running = false;
    private Scoreboard scoreboard;
    private List<BukkitTask> phaseTasks = Lists.newArrayList();

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
        bootstrap.getScoreboardManager().clearScoreboard();
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

    public List<BukkitTask> getTasks() {
        return phaseTasks;
    }

    public void addTask(BukkitTask task) {
        phaseTasks.add(task);
    }

    public void removeTask(BukkitTask task) {
        task.cancel();
        phaseTasks.remove(task);
    }
}
