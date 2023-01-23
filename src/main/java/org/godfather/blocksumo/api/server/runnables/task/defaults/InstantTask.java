package org.godfather.blocksumo.api.server.runnables.task.defaults;

import org.godfather.blocksumo.api.server.runnables.task.MinigameTask;

import java.util.function.Consumer;

public abstract class InstantTask<T> implements MinigameTask<T>, Consumer<T> {

    @Override
    public void accept(T t) {
        execute(t);
    }
}
