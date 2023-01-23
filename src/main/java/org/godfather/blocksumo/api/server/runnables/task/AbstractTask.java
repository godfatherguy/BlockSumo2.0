package org.godfather.blocksumo.api.server.runnables.task;

import org.godfather.blocksumo.api.server.runnables.RunnableManager;

import java.util.UUID;

public abstract class AbstractTask<T> implements MinigameTask<T> {

    protected UUID uuid;

    public AbstractTask() {
        do {
            uuid = UUID.randomUUID();
        } while (RunnableManager.getTask(uuid).isPresent());
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }
}
