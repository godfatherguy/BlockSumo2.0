package org.godfather.blocksumo;

import org.godfather.blocksumo.api.MinigamePlugin;
import org.godfather.blocksumo.bukkit.commands.CommandSound;

public final class BlockSumoPlugin extends MinigamePlugin {

    public BlockSumoPlugin() {
        super(new BlockSumoBootstrap());
    }

    @Override
    public void onEnable() {
        super.onEnable();

        getCommand("sound").setExecutor(new CommandSound());
    }
}
