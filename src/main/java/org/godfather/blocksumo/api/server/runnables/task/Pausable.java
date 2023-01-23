package org.godfather.blocksumo.api.server.runnables.task;

public interface Pausable {

    void pause();

    void resume();

    void cancel();
}
