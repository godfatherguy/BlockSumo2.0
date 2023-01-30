package org.godfather.blocksumo.api.game.phases.defaults.lobby;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.phases.GamePhase;
import org.godfather.blocksumo.api.game.phases.defaults.lobby.items.ItemBack;
import org.godfather.blocksumo.api.game.phases.defaults.lobby.items.ItemSettings;
import org.godfather.blocksumo.api.items.ItemManager;
import org.godfather.blocksumo.api.utils.MapUtils;
import org.godfather.blocksumo.api.utils.Utils;
import org.godfather.blocksumo.api.utils.messages.MessageType;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public final class LobbyPhase extends GamePhase {

    public final static int requiredPlayers = 2;
    public final static int maxPlayers = 16;

    public LobbyPhase(Bootstrap bootstrap) {
        super(bootstrap);
    }

    public void onLoad() {
        ItemManager.registerInteractable("item-back", new ItemBack());
        ItemManager.registerInteractable("item-settings", new ItemSettings());
    }

    public void onUnload() {

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

        if (Bukkit.getOnlinePlayers().size() + 1 >= requiredPlayers)
            Bukkit.getScheduler().runTaskLater(bootstrap.getPlugin(), () -> parentGame.nextPhase(), 2L);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setHealth(player.getMaxHealth());
        player.setLevel(0);
        player.setExp(0);
        player.setFoodLevel(40);
        player.setSaturation(100);
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.setAllowFlight(player.isOp() || player.hasPermission("playground.admin"));

        event.setJoinMessage(ChatColor.GRAY + player.getName() + " §eè entrato (§a" + Bukkit.getOnlinePlayers().size() + "§e/§a" + maxPlayers + "§e)!");

        if (Bukkit.getOnlinePlayers().size() == 1 || parentGame.getHoster().isEmpty()) {

            if (parentGame.isHostable() || (player.isOp() || player.hasPermission("playground.admin"))) {
                parentGame.setHoster(player);
                Bootstrap.LOGGER.info("§cHoster " + player.getName() + " impostato!");
            }
        }

        if (ItemManager.getInteractable("item-back").isPresent())
            player.getInventory().setItem(8, ItemManager.getInteractable("item-back").get().getBuilder().get());
    }

    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent event) {
        if (event.getSpawnLocation().getWorld().getDifficulty() != Difficulty.PEACEFUL)
            event.getSpawnLocation().getWorld().setDifficulty(Difficulty.PEACEFUL);

        event.setSpawnLocation(MapUtils.getLocation(bootstrap, "lobby"));
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
        Utils.teleport(player, MapUtils.getLocation(bootstrap, "lobby"));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Utils.sendMessageAll(MessageType.CHAT, ChatColor.GRAY + player.getName() + " §eè uscito!");

        if (parentGame.getHoster().isEmpty())
            return;

        if (!parentGame.getHoster().get().getUniqueId().equals(player.getUniqueId()))
            return;

        parentGame.newHoster();
    }

    @EventHandler
    public void onInteract(HangingBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(HangingPlaceEvent event) {
        event.setCancelled(true);
    }
}
