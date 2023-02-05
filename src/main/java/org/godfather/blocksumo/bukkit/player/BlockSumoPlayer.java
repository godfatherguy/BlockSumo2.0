package org.godfather.blocksumo.bukkit.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.game.player.GamePlayer;

public class BlockSumoPlayer extends GamePlayer {

    private GameColor color;
    private int lives = 5;
    private int kills = 0;

    public BlockSumoPlayer(Player player) {
        super(player);
    }

    public int getLives() {
        return lives;
    }

    public void removeLife() {
        lives--;
    }

    public int getKills() {
        return kills;
    }

    public void addKill() {
        kills++;
    }

    public String getFormattedName() {
        return color.getChatColor() + player.getName() + " " + getFormattedLives();
    }

    public void setColor(GameColor color) {
        this.color = color;
    }

    public GameColor getColor() {
        return color;
    }

    public String getFormattedLives() {
        ChatColor livesColor;

        switch(lives) {
            case 4, 3 -> livesColor = ChatColor.YELLOW;
            case 2 -> livesColor = ChatColor.GOLD;
            case 1 -> livesColor = ChatColor.RED;
            default -> livesColor = ChatColor.GREEN;
        }

        return livesColor + "" + lives;
    }
}
