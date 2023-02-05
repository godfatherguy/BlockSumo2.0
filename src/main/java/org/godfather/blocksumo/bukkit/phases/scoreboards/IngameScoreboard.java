package org.godfather.blocksumo.bukkit.phases.scoreboards;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.server.scoreboard.PhaseScoreboard;
import org.godfather.blocksumo.bukkit.player.BlockSumoPlayer;
import org.godfather.blocksumo.bukkit.player.BlockSumoPlayerManager;

import java.text.SimpleDateFormat;
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
    public List<String> getScoreboard(Player player) {
        List<String> list = Lists.newArrayList();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

        if (bootstrap.getPlayerManager().getProfile(player).isEmpty())
            return Collections.emptyList();

        BlockSumoPlayer gPlayer = (BlockSumoPlayer) bootstrap.getPlayerManager().getProfile(player).get();

        list.add(ChatColor.GRAY + date.format(new Date()));
        list.add("");
        list.add("§f§lGiocatori rimasti:");

        List<BlockSumoPlayer> players = ((BlockSumoPlayerManager) bootstrap.getPlayerManager()).sortByLives();

        if (players.size() < 8 || players.subList(0, 8).contains(gPlayer)) {
            for (BlockSumoPlayer blockSumoPlayer : players) {
                list.add(blockSumoPlayer.getFormattedName());
            }

        } else {
            for (int i = 0; i < 6; i++) {
                list.add(players.get(i).getFormattedName());
            }

            list.add("§7...");
            list.add(gPlayer.getFormattedName());
        }

        list.add("   ");
        list.add("§fUccisioni: §c" + gPlayer.getKills());
        list.add("    ");
        list.add("§eplay.godfather.it   ");

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
