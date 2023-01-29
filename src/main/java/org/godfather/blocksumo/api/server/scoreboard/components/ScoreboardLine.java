package org.godfather.blocksumo.api.server.scoreboard.components;

import org.bukkit.ChatColor;

public class ScoreboardLine {

    private final int position;
    private final String string;

    public ScoreboardLine(int position, String string) {
        this.position = position;
        this.string = string;
    }

    public int getPos() {
        return position;
    }

    public ChatColor getAssociated() {
        return switch (position) {
            case 0 -> ChatColor.DARK_GREEN;
            case 1 -> ChatColor.RED;
            case 2 -> ChatColor.YELLOW;
            case 3 -> ChatColor.GREEN;
            case 4 -> ChatColor.AQUA;
            case 5 -> ChatColor.BLACK;
            case 6 -> ChatColor.BLUE;
            case 7 -> ChatColor.DARK_AQUA;
            case 8 -> ChatColor.DARK_BLUE;
            case 9 -> ChatColor.DARK_GRAY;
            case 10 -> ChatColor.DARK_PURPLE;
            case 11 -> ChatColor.DARK_RED;
            case 12 -> ChatColor.GOLD;
            case 13 -> ChatColor.GRAY;
            case 15 -> ChatColor.LIGHT_PURPLE;
            default -> ChatColor.WHITE;
        };
    }

    public String getString() {
        return string;
    }
}
