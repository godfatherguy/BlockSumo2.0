package org.godfather.blocksumo.bukkit.phases.scoreboards;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.server.scoreboard.PhaseScoreboard;
import org.godfather.blocksumo.api.server.scoreboard.components.ScoreboardLine;
import org.godfather.blocksumo.bukkit.player.BlockSumoPlayer;
import org.godfather.blocksumo.bukkit.player.BlockSumoPlayerManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class IngameScoreboard extends PhaseScoreboard {

    public IngameScoreboard(Bootstrap bootstrap) {
        super(bootstrap);
    }

    @Override
    public String getTitle() {
        return "§e§lBLOCK SUMO";
    }

    @Override
    public List<ScoreboardLine> getScoreboard(Player player) {
        List<ScoreboardLine> list = new ArrayList<>();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

        if (bootstrap.getPlayerManager().getProfile(player).isEmpty())
            return Collections.emptyList();

        BlockSumoPlayer gPlayer = (BlockSumoPlayer) bootstrap.getPlayerManager().getProfile(player).get();

        list.add(build(15, ChatColor.GRAY + date.format(new Date())));
        list.add(build(14, ""));
        list.add(build(13, "§f§lGiocatori rimasti:"));

        List<BlockSumoPlayer> players = ((BlockSumoPlayerManager) bootstrap.getPlayerManager()).sortByLives();
        int actualLine = 12;

        if (players.size() < 8 || players.subList(0, 8).contains(gPlayer)) {
            for (BlockSumoPlayer blockSumoPlayer : players) {
                list.add(build(actualLine, blockSumoPlayer.getFormattedName()));
                actualLine--;
            }
        } else {
            for (int i = 0; i < 6; i++) {
                list.add(build(actualLine, players.get(i).getFormattedName()));
                actualLine--;
            }
            actualLine--;
            list.add(build(actualLine, "§7..."));
            actualLine--;
            list.add(build(actualLine, gPlayer.getFormattedName()));
            actualLine--;
        }

        list.add(build(actualLine, "   "));
        list.add(build(actualLine - 1, "§fUccisioni: §c" + gPlayer.getKills()));
        list.add(build(actualLine - 2, "    "));
        list.add(build(actualLine - 3, "§eplay.godfather.it   "));

        return list;
    }

    @Override
    public String perPlayerPrefix(Player player) {
        if (bootstrap.getPlayerManager().getProfile(player).isEmpty())
            return "";

        BlockSumoPlayer gPlayer = (BlockSumoPlayer) bootstrap.getPlayerManager().getProfile(player).get();

        return gPlayer.getColor().getChatColor() + "";
    }

    @Override
    public String perPlayerSuffix(Player player) {
        if (bootstrap.getPlayerManager().getProfile(player).isEmpty())
            return "";

        BlockSumoPlayer gPlayer = (BlockSumoPlayer) bootstrap.getPlayerManager().getProfile(player).get();

        return " " + gPlayer.getFormattedLives();
    }
}
