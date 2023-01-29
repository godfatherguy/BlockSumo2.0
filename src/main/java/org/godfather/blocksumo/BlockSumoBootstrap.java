package org.godfather.blocksumo;

import org.bukkit.Difficulty;
import org.bukkit.event.EventPriority;
import org.bukkit.event.world.WorldLoadEvent;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.server.events.ServerFastEvent;
import org.godfather.blocksumo.bukkit.BlockSumoDescription;
import org.godfather.blocksumo.bukkit.manager.BlockSumoConfigManager;
import org.godfather.blocksumo.bukkit.manager.BlockSumoGameManager;

public class BlockSumoBootstrap extends Bootstrap {

    @Override
    protected void onLoad() {
        setConfigManager(new BlockSumoConfigManager(this));
        setGameManager(new BlockSumoGameManager(this));
        setDescription(new BlockSumoDescription());

        registerVariable(ServerFastEvent.builder(WorldLoadEvent.class)
                .consumer(event -> {
                    if (event.getWorld() != null) {
                        event.getWorld().setAutoSave(false);
                        event.getWorld().setTime(1000);
                        event.getWorld().setGameRuleValue("doDaylightCycle", "false");
                        event.getWorld().setDifficulty(Difficulty.PEACEFUL);
                        event.getWorld().setGameRuleValue("naturalRegeneration", "false");
                    }
                }).priority(EventPriority.MONITOR).build());
    }

    @Override
    protected void onReload() {

    }

    @Override
    protected void onUnload() {

    }
}
