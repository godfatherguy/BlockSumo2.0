package org.godfather.blocksumo.api.utils.nms;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;

public final class Reflection {

    private final static Class<?> CHAT_COMPONENT = getNMSClass("IChatBaseComponent");
    private final static Class<?> PACKET = getNMSClass("Packet");
    private final static Class<?> PACKET_CHAT = getNMSClass("PacketPlayOutChat");
    private final static Class<?> PACKET_TITLE = getNMSClass("PacketPlayOutTitle");

    /**
     * @author @ImBuzz
     * @apiNote Send an action bar using Minecraft's Packets
     */
    public static void sendActionbar(Player player, String message) {
        try {

            assert PACKET_CHAT != null;
            Constructor<?> constructor = PACKET_CHAT.getConstructor(CHAT_COMPONENT, byte.class);

            assert CHAT_COMPONENT != null;
            Object chat_component = CHAT_COMPONENT.getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + message + "\"}");
            Object packet = constructor.newInstance(chat_component, (byte) 2);
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);

            playerConnection.getClass().getMethod("sendPacket", PACKET).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public static void sendTitle(Player player, String title, String subtitle) {
        sendTitle(player, title, subtitle, 0, 40, 0);
    }

    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int time, int fadeOut) {
        sendTitle(Collections.singletonList(player), title, subtitle, fadeIn, time, fadeOut);
    }

    public static void sendTitle(List<Player> players, String title, String subtitle, int fadeIn, int time, int fadeOut) {
        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
        IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");

        PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle packetSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
        PacketPlayOutTitle packetLength = new PacketPlayOutTitle(fadeIn, time, fadeOut);

        for (Player player : players) {
            sendPacket(player, packetTitle);
            if (!subtitle.isEmpty()) sendPacket(player, packetSubTitle);
            sendPacket(player, packetLength);
        }
    }

    public static void sendPacket(Player player, Packet<?> packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    private static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }
}
