package org.godfather.blocksumo.bukkit.phases.scoreboards;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.phases.defaults.lobby.LobbyPhase;
import org.godfather.blocksumo.api.server.scoreboard.PhaseScoreboard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LobbyScoreboard extends PhaseScoreboard {

    public LobbyScoreboard(Bootstrap bootstrap) {
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

        list.add(ChatColor.GRAY + date.format(new Date()));
        list.add("");
        list.add("§fMappa: §a" + bootstrap.getMapManager().getMap().getName());
        list.add("§fGiocatori: §a" + Bukkit.getOnlinePlayers().size() + "/" + LobbyPhase.maxPlayers);
        list.add(" ");
        list.add("§fAttendendo altri §a" + (LobbyPhase.requiredPlayers - Bukkit.getOnlinePlayers().size()));
        list.add("§fgiocatori per iniziare.");
        list.add("   ");
        list.add("Versione: §a" + bootstrap.getPlugin().getDescription().getVersion());
        list.add("    ");
        list.add("§eplay.godfather.it   ");

        return list;
    }

    @Override
    public String perPlayerPrefix(Player player) {
        return ChatColor.GRAY + "";
    }
}
