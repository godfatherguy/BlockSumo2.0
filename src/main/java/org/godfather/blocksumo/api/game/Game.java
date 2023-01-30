package org.godfather.blocksumo.api.game;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.phases.GamePhase;
import org.godfather.blocksumo.api.items.ItemManager;
import org.godfather.blocksumo.api.server.scoreboard.Scoreboard;
import org.godfather.blocksumo.api.utils.Utils;

import java.util.Optional;
import java.util.UUID;

public class Game {

    protected final Bootstrap bootstrap;
    protected final UUID uuid;
    private GamePhase actualPhase;
    private boolean hostable = false;
    private Player host = null;

    public Game(Bootstrap bootstrap, GamePhase gamePhase) {
        this.bootstrap = bootstrap;
        uuid = UUID.randomUUID();
        actualPhase = gamePhase;
    }

    public void nextPhase() {
        actualPhase.end();
        actualPhase.onUnload();

        if (actualPhase.getNextPhase().isEmpty()) {
            Bukkit.shutdown();
            return;
        }

        this.actualPhase = (GamePhase) actualPhase.getNextPhase().get();
        actualPhase.start();
        Bootstrap.LOGGER.info("§9Passato alla fase successiva.");
    }

    public void previousPhase() {
        if (actualPhase.getPreviousPhase().isEmpty())
            return;

        actualPhase.end();
        actualPhase.onUnload();
        this.actualPhase = (GamePhase) actualPhase.getPreviousPhase().get();

        actualPhase.start();
    }

    public GamePhase getActualPhase() {
        return actualPhase;
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean isHostable() {
        return hostable;
    }

    protected void setHostable(boolean hostable) {
        this.hostable = hostable;
    }

    public Optional<Player> getHoster() {
        return Optional.ofNullable(host);
    }

    public void setHoster(Player player) {
        this.host = player;

        if(player == null)
            return;

        if (ItemManager.getInteractable("item-settings").isPresent())
            player.getInventory().setItem(4, ItemManager.getInteractable("item-settings").get().getBuilder().get());

        Bukkit.getScheduler().runTaskLater(bootstrap.getPlugin(), () -> player.sendMessage("§cSei diventato l'host di questa partita."), 2L);
    }
}
