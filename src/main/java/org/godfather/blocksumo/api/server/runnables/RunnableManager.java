package org.godfather.blocksumo.api.server.runnables;

import org.godfather.blocksumo.api.server.runnables.task.MinigameTask;
import org.godfather.blocksumo.api.server.runnables.task.Pausable;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class RunnableManager {

    private final static Map<UUID, MinigameTask<?>> tasks = new ConcurrentHashMap<>();

    public static Optional<MinigameTask<?>> getTask(UUID uuid) {
        return Optional.ofNullable(tasks.get(uuid));
    }

    public static void registerTask(MinigameTask<?> task) {
        tasks.put(task.getUUID(), task);
    }

    public static void unregisterTask(UUID uuid) {
        tasks.remove(uuid);
        getTask(uuid).ifPresent(task -> {
            if (task instanceof Pausable pausable)
                pausable.cancel();
        });
    }

    public static void pauseAll() {
        tasks.values().forEach(task -> {
            if (task instanceof Pausable pausable)
                pausable.pause();
        });
    }

    public static void resumeAll() {
        tasks.values().forEach(task -> {
            if (task instanceof Pausable pausable)
                pausable.resume();
        });
    }

    public static void clear() {
        tasks.values().forEach(task -> {
            if (task instanceof Pausable pausable)
                pausable.cancel();
        });
        tasks.clear();
    }
}
