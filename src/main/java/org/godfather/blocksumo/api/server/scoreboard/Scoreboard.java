package org.godfather.blocksumo.api.server.scoreboard;

import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.server.scoreboard.components.ScoreboardLine;

import java.util.List;

public interface Scoreboard {

    String getTitle();

    List<String> getScoreboard(Player player);

    default String perPlayerPrefix(Player player) {
        return "";
    }

    default String perPlayerSuffix(Player player) {
        return "";
    }

    default ScoreboardLine build(int i, String s) {
        return new ScoreboardLine(i, s);
    }
}
