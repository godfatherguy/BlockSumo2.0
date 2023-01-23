package org.godfather.blocksumo.api.server;

import java.util.Arrays;
import java.util.Optional;

public enum ServerPhase {

    LOAD(0),
    RUN(1),
    FREEZE(2),
    END(3);

    private final int index;

    ServerPhase(int index) {
        this.index = index;
    }

    public Optional<ServerPhase> getNextPhase() {
        return Optional.ofNullable((ServerPhase) Arrays.stream(ServerPhase.values()).filter(phase -> phase.index - index == 1).toArray()[0]);
    }

    public Optional<ServerPhase> getPreviousPhase() {
        return Optional.ofNullable((ServerPhase) Arrays.stream(ServerPhase.values()).filter(phase -> index - phase.index == 1).toArray()[0]);
    }
}
