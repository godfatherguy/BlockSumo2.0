package org.godfather.blocksumo.api.game.phases.defaults.lobby;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.phases.GamePhase;
import org.godfather.blocksumo.api.server.scoreboard.Scoreboard;

public final class LobbyPhase extends GamePhase {

    public LobbyPhase(Bootstrap bootstrap) {
        super(bootstrap);
    }

    public void onLoad() {

    }

    protected void onUnload() {

    }

    @Override
    public Scoreboard getScoreboard() {
        return null;
    }

    @Override
    public boolean joinEnabled() {
        return true;
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
        if(event.getEntity() instanceof Player player)
            player.setFoodLevel(20);

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
