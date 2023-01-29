package org.godfather.blocksumo.bukkit.player;

import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.game.player.GamePlayer;

public class BlockSumoPlayer extends GamePlayer {

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
}
