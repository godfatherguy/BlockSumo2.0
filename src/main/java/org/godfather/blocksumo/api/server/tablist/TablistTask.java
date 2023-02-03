package org.godfather.blocksumo.api.server.tablist;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.List;

public class TablistTask extends BukkitRunnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(this::tablist);
    }

    private final Tablist tablist;

    public TablistTask(Tablist tablist) {
        this.tablist = tablist;
    }

    private String concatenate(List<String> list) {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for(String line: list) {
            builder.append(list.get(count));

            if(count + 1 < list.size())
                builder.append("\n");

            count++;
        }
        return builder.toString();
    }

    private void tablist(Player player) {
        IChatBaseComponent tabHeader = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + concatenate(tablist.getTablistHeader(player)) + "\"}");
        IChatBaseComponent tabFooter = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + concatenate(tablist.getTablistFooter(player)) + "\"}");

        PacketPlayOutPlayerListHeaderFooter headPacket = new PacketPlayOutPlayerListHeaderFooter(tabHeader);

        try {
            Field field = headPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headPacket, tabFooter);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(headPacket);
        }
    }
}
