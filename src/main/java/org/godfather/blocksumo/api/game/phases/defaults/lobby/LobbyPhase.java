package org.godfather.blocksumo.api.game.phases.defaults.lobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
import org.godfather.blocksumo.api.game.phases.defaults.lobby.items.ItemBack;
import org.godfather.blocksumo.api.items.ItemManager;
import org.godfather.blocksumo.api.server.scoreboard.Scoreboard;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public final class LobbyPhase extends GamePhase {

    private final int requiredPlayers = 2;
    private final int maxPlayers = 16;

    public LobbyPhase(Bootstrap bootstrap) {
        super(bootstrap);
    }

    public void onLoad() {
        ItemManager.registerInteractable("item-back", new ItemBack());
    }

    public void onUnload() {

    }

    @Override
    public Scoreboard getScoreboard() {
        return null;
    }

    @Override
    public boolean joinEnabled() {
        return true;
    }

    public int getRequiredPlayers() {
        return requiredPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
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
        if (!joinEnabled()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "§cAttendi un momento...");
            return;
        }
        if (Bukkit.getOnlinePlayers().size() + 1 >= maxPlayers) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, "§cLa partita è piena.");
            return;
        }

        if (Bukkit.getOnlinePlayers().size() + 1 <= requiredPlayers)
            return;

        parentGame.nextPhase();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setHealth(player.getMaxHealth());
        player.setLevel(0);
        player.setExp(0);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.setAllowFlight(player.isOp() || player.hasPermission("playground.admin"));

        event.setJoinMessage(ChatColor.GRAY + player.getName() + " §eè entrato (§a" + Bukkit.getOnlinePlayers().size() + "§e/§a" + maxPlayers + "§e)!");

        if (ItemManager.getInteractable("item-back").isPresent())
            player.getInventory().setItem(8, ItemManager.getInteractable("item-back").get().getBuilder().get());
    }

    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent event) {
        event.setSpawnLocation(new Location(Bukkit.getWorld("world"), 0, 79, 0));

        //todo cambiare spawn
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        player.spigot().respawn();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getLocation().getY() >= 0.0)
            return;

        event.setCancelled(true);
        player.teleport(new Location(Bukkit.getWorld("world"), 0, 79, 0));
        //todo cambiare spawn
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(ChatColor.GRAY + player.getName() + " §eè uscito!");
    }
}
