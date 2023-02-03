package org.godfather.blocksumo.api.game.phases;

import com.google.common.collect.Lists;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.Game;
import org.godfather.blocksumo.api.server.scoreboard.Scoreboard;
import org.godfather.blocksumo.api.server.tablist.Tablist;
import org.godfather.blocksumo.api.server.tablist.TablistTask;

import java.util.List;
import java.util.Optional;

public abstract class GamePhase implements Listener, Phase {

    protected Game parentGame;
    protected final Bootstrap bootstrap;
    protected Phase previousPhase = null;
    protected Phase nextPhase = null;
    protected boolean running = false;
    private Scoreboard scoreboard;
    private final List<BukkitTask> phaseTasks = Lists.newArrayList();
    private Tablist tablist;

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

        if (scoreboard != null)
            bootstrap.getScoreboardManager().startScoreboard(scoreboard);

        if (tablist != null)
            addTask(new TablistTask(tablist).runTaskTimer(bootstrap.getPlugin(), 1L, 1L));

    }

    public final void end() {
        running = false;
        HandlerList.unregisterAll(this);
        bootstrap.getScoreboardManager().clearScoreboard();

        phaseTasks.forEach(BukkitTask::cancel);
        phaseTasks.clear();
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

    @Override
    public void setTablist(Tablist tablist) {
        this.tablist = tablist;
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
