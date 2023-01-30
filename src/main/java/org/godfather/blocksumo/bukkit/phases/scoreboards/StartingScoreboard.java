package org.godfather.blocksumo.bukkit.phases.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.phases.defaults.lobby.LobbyPhase;
import org.godfather.blocksumo.api.server.scoreboard.PhaseScoreboard;
import org.godfather.blocksumo.api.server.scoreboard.components.ScoreboardLine;
import org.godfather.blocksumo.api.game.phases.defaults.starting.StartingPhase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StartingScoreboard extends PhaseScoreboard {

    public StartingScoreboard(Bootstrap bootstrap) {
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

        list.add(build(10, ChatColor.GRAY + date.format(new Date())));
        list.add(build(9, ""));
        list.add(build(8, "§fMappa: §a" + bootstrap.getMapManager().getMap().getName()));
        list.add(build(7, "§fGiocatori: §a" + Bukkit.getOnlinePlayers().size() + "/" + LobbyPhase.maxPlayers));
        list.add(build(6, " "));
        list.add(build(5, "§fInizio in §a" + ((StartingPhase) bootstrap.getGameManager().getRunningGame().getActualPhase()).getTime() + "s"));
        list.add(build(4, "   "));
        list.add(build(3, "§fVersione: §a" + bootstrap.getPlugin().getDescription().getVersion()));
        list.add(build(2, "    "));
        list.add(build(1, "§eplay.godfather.it   "));

        return list;
    }

    @Override
    public String perPlayerPrefix(Player player) {
        return ChatColor.GRAY + "";
    }
}
