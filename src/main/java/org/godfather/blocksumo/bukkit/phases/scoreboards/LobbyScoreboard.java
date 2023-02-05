package org.godfather.blocksumo.bukkit.phases.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.phases.defaults.lobby.LobbyPhase;
import org.godfather.blocksumo.api.server.scoreboard.PhaseScoreboard;
import org.godfather.blocksumo.api.server.scoreboard.components.ScoreboardLine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public List<ScoreboardLine> getScoreboard(Player player) {
        List<ScoreboardLine> list = new ArrayList<>();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

        list.add(build(11, ChatColor.GRAY + date.format(new Date())));
        list.add(build(10, ""));
        list.add(build(9, "§fMappa: §a" + bootstrap.getMapManager().getMap().getName()));
        list.add(build(8, "§fGiocatori: §a" + Bukkit.getOnlinePlayers().size() + "/" + LobbyPhase.maxPlayers));
        list.add(build(7, " "));
        list.add(build(6, "§fAttendendo altri §a" + (LobbyPhase.requiredPlayers - Bukkit.getOnlinePlayers().size())));
        list.add(build(5, "§fgiocatori per iniziare."));
        list.add(build(4, "   "));
        list.add(build(3, "Versione: §a" + bootstrap.getPlugin().getDescription().getVersion()));
        list.add(build(2, "    "));
        list.add(build(1, "§eplay.godfather.it   "));

        return list;
    }

    @Override
    public String perPlayerPrefix(Player player) {
        return ChatColor.GRAY + "";
    }
}
