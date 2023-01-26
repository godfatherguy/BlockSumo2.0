package org.godfather.blocksumo;

import org.godfather.blocksumo.api.MinigamePlugin;

public final class BlockSumoPlugin extends MinigamePlugin {

    private BlockSumoPlugin() {
        super(new BlockSumoBootstrap());
    }
}
