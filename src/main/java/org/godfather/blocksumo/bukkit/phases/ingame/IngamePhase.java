package org.godfather.blocksumo.bukkit.phases.ingame;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.godfather.blocksumo.api.Bootstrap;
import org.godfather.blocksumo.api.game.phases.GamePhase;
import org.godfather.blocksumo.api.items.ItemBuilder;
import org.godfather.blocksumo.api.server.runnables.utils.Countdown;
import org.godfather.blocksumo.api.utils.MapUtils;
import org.godfather.blocksumo.api.utils.Utils;
import org.godfather.blocksumo.api.utils.WorldUtils;
import org.godfather.blocksumo.api.utils.messages.MessageType;
import org.godfather.blocksumo.api.utils.nms.Reflection;
import org.godfather.blocksumo.bukkit.player.BlockSumoPlayer;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class IngamePhase extends GamePhase {

    private final ItemBuilder itemScissor = new ItemBuilder(Material.SHEARS)
            .setName("§aCesoia magica")
            .setUnbreakable()
            .addUnsafeEnchantment(Enchantment.DIG_SPEED, 5)
            .hideAttributes();

    public IngamePhase(Bootstrap bootstrap) {
        super(bootstrap);
    }

    @Override
    public void onLoad() {
        addTask(Bukkit.getScheduler().runTaskTimer(bootstrap.getPlugin(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (player.getUniqueId().equals(target.getUniqueId())) continue;

                    if (bootstrap.getPlayerManager().getProfile(player).isPresent()
                            && !bootstrap.getPlayerManager().getProfile(player).get().isSpectator()) {

                        player.showPlayer(target);
                        continue;
                    }

                    player.hidePlayer(target);
                }
            }
        }, 1L, 1L));

        bootstrap.getPlayerManager().load();
    }

    @Override
    public void onUnload() {

    }

    @Override
    public boolean joinEnabled() {
        return true;
    }

    private void setupSpectator(Player player) {
        player.setHealth(player.getMaxHealth());
        player.setLevel(0);
        player.setExp(0);
        player.setFoodLevel(40);
        player.setSaturation(100);
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setAllowFlight(true);
        player.setFlying(true);

        //todo items dello spettatore
    }

    private void setupPlayer(Player player) {
        player.setHealth(player.getMaxHealth());
        player.setLevel(0);
        player.setExp(0);
        player.setFoodLevel(40);
        player.setSaturation(100);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setAllowFlight(false);
        player.setFlying(false);

        player.getInventory().setItem(0, itemScissor.get());
        //todo setup altri items

        Utils.teleport(player, WorldUtils.getRandomLocation(MapUtils.getLocation(bootstrap, "spawn"), 80, 20));
    }

    private void killPlayer(BlockSumoPlayer player) {
        player.removeLife();
        setupSpectator(player.getHandle());

        if (player.getLives() == 0) {
            player.getHandle().sendMessage("§cSei stato eliminato! Ora sei uno spettatore.");
            return;
        }

        Countdown.builder()
                .startFrom(3)
                .onStart(time -> Reflection.sendTitle(player.getHandle(), "SEI MORTO!", "§eRientrerai tra §c" + time + "§e secondi", 0, 21, 0))
                .onRepeat(time -> Reflection.sendTitle(player.getHandle(), "SEI MORTO!", "§eRientrerai tra §c" + time + "§e secondi", 0, 21, 0))
                .onFinish(() -> {
                    Reflection.sendTitle(player.getHandle(), "", "", 0, 1, 0);
                    setupPlayer(player.getHandle());
                })
                .start(bootstrap);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player))
            return;

        event.setDamage(0.0);
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player))
            return;

        event.setDamage(0.0);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        //todo solo se sono blocchi piazzati
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
        if (joinEnabled())
            return;

        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "§cAttendi un momento...");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        setupSpectator(player);

        if (bootstrap.getPlayerManager().isInGame(player) && bootstrap.getPlayerManager().getProfile(player).isPresent()) {
            BlockSumoPlayer gPlayer = (BlockSumoPlayer) bootstrap.getPlayerManager().getProfile(player).get();
            event.setJoinMessage(ChatColor.GRAY + player.getName() + " §eè rientrato in partita.");

            killPlayer(gPlayer);
            return;
        }
        player.sendMessage("§7Sei diventato uno spettatore.");
    }

    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent event) {
        event.setSpawnLocation(MapUtils.getLocation(bootstrap, "lobby"));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if(!bootstrap.getPlayerManager().isInGame(player) || bootstrap.getPlayerManager().getProfile(player).isEmpty())
            return;
        BlockSumoPlayer gPlayer = (BlockSumoPlayer) bootstrap.getPlayerManager().getProfile(player).get();

        player.spigot().respawn();
        killPlayer(gPlayer);
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
