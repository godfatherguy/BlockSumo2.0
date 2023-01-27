package org.godfather.blocksumo.api.server.runnables.utils;

import org.bukkit.scheduler.BukkitRunnable;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.server.runnables.task.Pausable;

import java.util.Optional;
import java.util.function.Consumer;

public final class Countdown extends BukkitRunnable implements Pausable {

    @Override
    public void run() {
        if (paused)
            return;

        if (actualTime == 0) {
            if (Optional.ofNullable(finish).isPresent())
                finish.accept(this);

            cancel();
            return;
        }

        actualTime--;

        if (Optional.ofNullable(repeat).isPresent())
            repeat.accept(this);
    }

    private int initialTime;
    private int actualTime;
    private Consumer<Countdown> start;
    private Consumer<Countdown> repeat;
    private Consumer<Countdown> finish;
    private boolean paused = false;

    public Countdown startFrom(int seconds) {
        this.actualTime = this.initialTime = seconds;
        return this;
    }

    public Countdown onStart(Consumer<Countdown> start) {
        this.start = start;
        return this;
    }

    public Countdown onRepeat(Consumer<Countdown> repeat) {
        this.repeat = repeat;
        return this;
    }

    public Countdown onFinish(Consumer<Countdown> finish) {
        this.finish = finish;
        return this;
    }

    public Countdown start(Bootstrap bootstrap) {
        runTaskTimer(bootstrap.getPlugin(), 1L, 20L);

        if (Optional.ofNullable(start).isPresent())
            start.accept(this);

        return this;
    }

    public static Countdown builder() {
        return new Countdown();
    }

    public int getActualTime() {
        return actualTime;
    }

    public int getInitialTime() {
        return initialTime;
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }
}
