package org.godfather.blocksumo.bukkit.phases;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.phases.GamePhase;
import org.godfather.blocksumo.api.game.phases.defaults.lobby.LobbyPhase;
import org.godfather.blocksumo.api.server.runnables.utils.Countdown;
import org.godfather.blocksumo.api.server.scoreboard.Scoreboard;
import org.godfather.blocksumo.api.utils.Utils;
import org.godfather.blocksumo.api.utils.messages.MessageType;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class StartingPhase extends GamePhase {

    private Countdown countdown;

    public StartingPhase(Bootstrap bootstrap) {
        super(bootstrap);
    }

    @Override
    public void onLoad() {
        countdown = Countdown.builder()
                .startFrom(30)
                .onStart(r -> alertPlayers())
                .onRepeat(r -> {
                    if (countdown.getActualTime() <= 5 || countdown.getActualTime() % 10 == 0) {
                        alertPlayers();
                    }
                })
                .onFinish(r -> {
                    parentGame.nextPhase();

                    Utils.sendMessageAll(MessageType.CHAT, "§aLa partita è iniziata!");
                    Utils.sendTitleAll(p -> "", p -> "", 0, 1, 0);
                })
                .start(bootstrap);
    }

    @Override
    public void onUnload() {
        countdown.cancel();
        countdown = null;
    }

    @Override
    public Scoreboard getScoreboard() {
        return null;
    }

    @Override
    public boolean joinEnabled() {
        return true;
    }

    private void alertPlayers() {
        String timeName = countdown.getActualTime() == 1 ? "secondo." : "secondi.";

        Utils.sendMessageAll(MessageType.CHAT, "§eLa partita inizia in "
                + Utils.getFormattedTime(countdown.getActualTime(), ChatColor.YELLOW) + ChatColor.YELLOW + timeName);

        Utils.sendTitleAll(p -> Utils.getFormattedTime(countdown.getActualTime(), ChatColor.YELLOW), p -> "", 0, 25, 0);
        Utils.playSoundAll(Sound.WOOD_CLICK, 1, 1.6F);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPickup(InventoryPickupItemEvent event) {
        event.getItem().remove();

        event.setCancelled(true);
    }

    @EventHandler
    public void onCraftingItem(CraftItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevel(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player)
            player.setFoodLevel(20);

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPreJoin(AsyncPlayerPreLoginEvent event) {
        if(!joinEnabled()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "§cAttendi un momento...");
            return;
        }
        if (Bukkit.getOnlinePlayers().size() + 1 < ((LobbyPhase) previousPhase).getMaxPlayers())
            return;

        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, "§cLa partita è piena.");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ((LobbyPhase) previousPhase).onJoin(event);
    }

    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent event) {
        ((LobbyPhase) previousPhase).onSpawn(event);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        player.spigot().respawn();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event) {
        ((LobbyPhase) previousPhase).onMove(event);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(ChatColor.GRAY + player.getName() + " §eè uscito!");

        if(Bukkit.getOnlinePlayers().size() >= ((LobbyPhase) previousPhase).getRequiredPlayers())
            return;

        parentGame.previousPhase();
        countdown.cancel();
        countdown = null;

        Utils.sendMessageAll(MessageType.CHAT, "§cGiocatori insufficienti per iniziare la partita.");
        Utils.playSoundAll(Sound.VILLAGER_NO, 1, 1);
    }
}
