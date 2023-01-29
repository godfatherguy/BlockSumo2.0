package org.godfather.blocksumo.api.server.scoreboard;

import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.server.manager.Manager;
import org.godfather.blocksumo.api.server.scoreboard.components.ScoreboardTask;

import java.util.Optional;

public final class ScoreboardManager extends Manager {

    private Scoreboard actualScoreboard = null;
    private ScoreboardTask task;

    public ScoreboardManager(Bootstrap bootstrap) {
        super(bootstrap);
    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected void onUnload() {
        clearScoreboard();
    }

    public void startScoreboard(Scoreboard scoreboard) {
        actualScoreboard = scoreboard;

        if(task != null)
            task.stop();

        task = new ScoreboardTask(bootstrap, actualScoreboard).start();
    }

    public void clearScoreboard() {
        actualScoreboard = null;

        if(task != null)
            task.stop();
    }

    public Optional<Scoreboard> getScoreboard() {
        return Optional.ofNullable(actualScoreboard);
    }
}
