package org.godfather.blocksumo.api.server.scoreboard.components;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.server.scoreboard.Scoreboard;

import java.util.Objects;

public class ScoreboardTask extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getScoreboard().equals(Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard()))
                show(player);
            else update(player);
            setPrefixSuffix(player);
        }
    }

    private final Bootstrap bootstrap;
    private final Scoreboard playerScoreboard;

    public ScoreboardTask(Bootstrap bootstrap, Scoreboard playerScoreboard) {
        this.bootstrap = bootstrap;
        this.playerScoreboard = playerScoreboard;
    }

    public ScoreboardTask start() {
        runTaskTimer(bootstrap.getPlugin(), 1L, 1L);
        return this;
    }

    public void stop() {
        cancel();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard());
        }
    }

    private void show(Player player) {
        org.bukkit.scoreboard.Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective(playerScoreboard.getTitle(), "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (ScoreboardLine line : playerScoreboard.getScoreboard(player)) {
            Team team = scoreboard.registerNewTeam(String.valueOf(line.getPos()));
            team.addEntry(line.getAssociated() + "");
            setupLine(team, line.getString());
            objective.getScore(line.getAssociated() + "").setScore(line.getPos());
        }

        player.setScoreboard(scoreboard);
    }

    private void update(Player player) {
        org.bukkit.scoreboard.Scoreboard scoreboard = player.getScoreboard();

        for (ScoreboardLine line : playerScoreboard.getScoreboard(player)) {
            if (scoreboard.getTeam(String.valueOf(line.getPos())) == null) {
                scoreboard.registerNewTeam(String.valueOf(line.getPos()));
            }

            Team team = scoreboard.getTeam(String.valueOf(line.getPos()));
            assert team != null;
            setupLine(team, line.getString());
        }
    }

    private void setPrefixSuffix(Player player) {
        org.bukkit.scoreboard.Scoreboard scoreboard = player.getScoreboard();

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (scoreboard.getTeam(players.getName()) == null)
                scoreboard.registerNewTeam(players.getName());

            Team team = scoreboard.getTeam(players.getName());
            assert team != null;

            team.setPrefix(playerScoreboard.perPlayerPrefix(players));
            team.setSuffix(playerScoreboard.perPlayerSuffix(players));
            if (team.hasEntry(players.getName())) continue;
            team.addEntry(players.getName());
        }
    }

    public void setupLine(Team team, String line) {
        if(line.length() <= 16) {
            team.setPrefix(line);
            return;
        }

        String firstPart = line.substring(0, 16);
        String secondPart = ChatColor.RESET + org.bukkit.ChatColor.getLastColors(firstPart) + line.substring(16);

        team.setPrefix(firstPart);
        team.setSuffix(secondPart);
    }
}
