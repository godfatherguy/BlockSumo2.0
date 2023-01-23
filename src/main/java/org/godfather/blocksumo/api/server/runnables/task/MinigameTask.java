package org.godfather.blocksumo.api.server.runnables.task;

import java.util.UUID;

public interface MinigameTask<T> {

    UUID getUUID();

    void execute(T paramT);
}
