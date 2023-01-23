package org.godfather.blocksumo.api.server.runnables.task.defaults;

import org.bukkit.scheduler.BukkitRunnable;
import org.godfather.blocksumo.api.MinigamePlugin;
import org.godfather.blocksumo.api.server.runnables.RunnableManager;
import org.godfather.blocksumo.api.server.runnables.task.AbstractTask;
import org.godfather.blocksumo.api.server.runnables.task.Pausable;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public abstract class RepeatedTask<T> extends AbstractTask<T> implements Consumer<T>, Pausable {

    private final MinigamePlugin plugin;
    protected final long after;
    protected final long delay;
    private boolean paused = false;
    private boolean cancelled = false;

    protected RepeatedTask(MinigamePlugin plugin, long after, long delay) {
        this.plugin = plugin;
        this.after = after;
        this.delay = delay;
    }

    @Override
    public void accept(T t) {
        AtomicLong actualDelay = new AtomicLong(delay);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (cancelled) {
                    cancel();
                    return;
                }

                if (paused)
                    return;

                if (actualDelay.get() == 0) {
                    execute(t);
                    actualDelay.set(delay);
                    return;
                }
                actualDelay.getAndDecrement();
            }
        }.runTaskTimer(plugin, after, 1L);

        RunnableManager.registerTask(this);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void cancel() {
        cancelled = true;
    }
}
