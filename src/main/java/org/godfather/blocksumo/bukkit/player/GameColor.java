package org.godfather.blocksumo.bukkit.player;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public enum GameColor {

    DARK_BLUE(ChatColor.DARK_BLUE, Color.fromRGB(11, 2, 115)),
    DARK_GREEN(ChatColor.DARK_GREEN, Color.fromRGB(10, 102, 0)),
    DARK_AQUA(ChatColor.DARK_AQUA, Color.fromRGB(0, 120, 98)),
    DARK_RED(ChatColor.DARK_RED, Color.fromRGB(122, 0, 0)),
    DARK_PURPLE(ChatColor.DARK_PURPLE, Color.fromRGB(128, 2, 150)),
    DARK_GRAY(ChatColor.DARK_GRAY, Color.fromRGB(82, 82, 82)),
    GOLD(ChatColor.GOLD, Color.fromRGB(255, 192, 46)),
    GRAY(ChatColor.GRAY, Color.fromRGB(181, 181, 181)),
    BLUE(ChatColor.BLUE, Color.fromRGB(87, 76, 217)),
    GREEN(ChatColor.GREEN, Color.fromRGB(85, 214, 71)),
    AQUA(ChatColor.AQUA, Color.fromRGB(68, 212, 185)),
    RED(ChatColor.RED, Color.fromRGB(237, 83, 83)),
    PINK(ChatColor.LIGHT_PURPLE, Color.fromRGB(231, 111, 252)),
    YELLOW(ChatColor.YELLOW, Color.fromRGB(255, 234, 8)),
    WHITE(ChatColor.WHITE, Color.WHITE);

    private final ChatColor chatColor;
    private final Color dyeColor;

    GameColor(ChatColor chatColor, Color dyeColor) {
        this.chatColor = chatColor;
        this.dyeColor = dyeColor;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public Color getDyeColor() {
        return dyeColor;
    }
}
