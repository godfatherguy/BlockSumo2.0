package org.godfather.blocksumo;

import org.godfather.blocksumo.api.MinigamePlugin;

public final class BlockSumoPlugin extends MinigamePlugin {

    public BlockSumoPlugin() {
        super(new BlockSumoBootstrap());
    }
}
