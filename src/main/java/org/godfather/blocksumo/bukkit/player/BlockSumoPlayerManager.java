package org.godfather.blocksumo.bukkit.player;

import com.google.common.collect.Lists;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.player.PlayerManager;
import org.godfather.blocksumo.api.utils.Utils;

import java.util.*;

public class BlockSumoPlayerManager extends PlayerManager<BlockSumoPlayer> {


    public BlockSumoPlayerManager(Bootstrap bootstrap) {
        super(bootstrap, BlockSumoPlayer.class);
    }

    @Override
    protected void onLoad() {
        registerAll();

        Set<GameColor> availableColors = EnumSet.allOf(GameColor.class);
        gamePlayers.values().forEach(player -> {
            GameColor hisColor;
            do {
                hisColor = Utils.getRandomInList(Arrays.stream(GameColor.values()).toList());
            } while (!availableColors.contains(hisColor));

            availableColors.remove(hisColor);
            player.setColor(hisColor);
        });
    }

    @Override
    protected void onUnload() {
        unregisterAll();
    }

    public List<BlockSumoPlayer> sortByLives() {
        List<BlockSumoPlayer> players = Lists.newArrayList(gamePlayers.values());
        players.sort(Comparator.comparingInt(BlockSumoPlayer::getLives));
        return players;
    }

    public List<BlockSumoPlayer> sortByKills() {
        List<BlockSumoPlayer> players = Lists.newArrayList(gamePlayers.values());
        players.sort(Comparator.comparingInt(BlockSumoPlayer::getKills));
        return players;
    }
}
