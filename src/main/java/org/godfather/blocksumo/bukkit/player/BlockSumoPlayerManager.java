package org.godfather.blocksumo.bukkit.player;

import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.player.PlayerManager;

public class BlockSumoPlayerManager extends PlayerManager<BlockSumoPlayer> {


    public BlockSumoPlayerManager(Bootstrap bootstrap) {
        super(bootstrap, BlockSumoPlayer.class);
    }

    @Override
    protected void onLoad() {
        registerAll();
    }

    @Override
    protected void onUnload() {
        unregisterAll();
    }
}
