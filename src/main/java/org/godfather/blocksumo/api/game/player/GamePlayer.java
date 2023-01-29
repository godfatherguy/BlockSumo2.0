package org.godfather.blocksumo.api.game.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.utils.Utils;

import java.util.UUID;

public abstract class GamePlayer {

    private final Player player;
    private boolean spectator = false;
    private boolean winner = false;

    public GamePlayer(Player player) {
        this.player = player;
    }

    public Player getHandle() {
        return player;
    }

    public UUID getUUID() {
        return player.getUniqueId();
    }

    public boolean hasPermission(String perm) {
        return player.hasPermission(perm);
    }

    public boolean isSpectator() {
        return spectator;
    }

    public void setSpectator(boolean spectator) {
        this.spectator = spectator;

        if (this.spectator) {
            //todo setup
        }
    }

    public void teleport(Location location) {
        Utils.teleport(player, location);
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
