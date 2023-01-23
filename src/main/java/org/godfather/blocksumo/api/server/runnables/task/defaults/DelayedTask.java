package org.godfather.blocksumo.api.server.runnables.task.defaults;

import org.bukkit.scheduler.BukkitRunnable;
import org.godfather.blocksumo.api.MinigamePlugin;
import org.godfather.blocksumo.api.server.runnables.RunnableManager;
import org.godfather.blocksumo.api.server.runnables.task.AbstractTask;
import org.godfather.blocksumo.api.server.runnables.task.Pausable;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public abstract class DelayedTask<T> extends AbstractTask<T> implements Consumer<T>, Pausable {

    private final MinigamePlugin plugin;
    protected final long delay;
    private boolean paused = false;
    private boolean cancelled = false;

    protected DelayedTask(MinigamePlugin plugin, long delay) {
        this.plugin = plugin;
        this.delay = delay;
    }

    @Override
    public void accept(T t) {
        AtomicLong actualDelay = new AtomicLong(delay);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(cancelled) {
                    cancel();
                    return;
                }

                if (paused)
                    return;

                if(actualDelay.get() == 0) {
                    execute(t);
                    cancel();
                    return;
                }
                actualDelay.getAndDecrement();
            }
        }.runTaskTimer(plugin, 1L, 1L);

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
