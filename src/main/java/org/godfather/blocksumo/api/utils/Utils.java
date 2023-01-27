package org.godfather.blocksumo.api.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.utils.messages.DefaultFontInfo;
import org.godfather.blocksumo.api.utils.messages.MessageBuilder;
import org.godfather.blocksumo.api.utils.messages.MessageFlag;
import org.godfather.blocksumo.api.utils.messages.MessageType;
import org.godfather.blocksumo.api.utils.nms.Reflection;

import java.util.*;
import java.util.function.Function;

@SuppressWarnings("unused")
public final class Utils {

    public static void sendMessage(MessageType type, Player player, String message, MessageFlag... flags) {
        MessageBuilder builder = new MessageBuilder(type, message).addFlags(flags);
        builder.send(player);
    }

    public static void sendMessageAll(MessageType type, Function<Player, String> message, MessageFlag... flags) {
        MessageBuilder builder = new MessageBuilder(type, message).addFlags(flags);
        Bukkit.getOnlinePlayers().forEach(builder::send);
    }

    public static void sendMessageAll(MessageType type, String message, MessageFlag... flags) {
        MessageBuilder builder = new MessageBuilder(type, message).addFlags(flags);
        Bukkit.getOnlinePlayers().forEach(builder::send);
    }

    public static void sendTitle(Player player, String title, String subTitle, int in, int stay, int out) {
        Reflection.sendTitle(player, title, subTitle, in, stay, out);
    }

    public static void sendTitleAll(Function<Player, String> title, Function<Player, String> subTitle, int in, int stay, int out) {
        Bukkit.getOnlinePlayers().forEach(player -> Reflection.sendTitle(player, title.apply(player), subTitle.apply(player), in, stay, out));
    }

    public static void playSound(Player player, Sound sound, float v, float p) {
        player.playSound(player.getLocation(), sound, v, p);
    }

    public static void playSoundAll(Sound sound, float v, float p) {
        Bukkit.getOnlinePlayers().forEach(player -> playSound(player, sound, v, p));
    }

    public static String centeredMessage(String message) {
        if (message == null || message.equals(""))
            return "";

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
            } else if (previousCode) {
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = 154 - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        return sb + message;
    }

    public static void hideFromPlayers(Player player) {
        Bukkit.getOnlinePlayers().forEach(players -> {
            if (!players.getUniqueId().equals(player.getUniqueId()))
                players.hidePlayer(player);
        });
    }

    public static void hidePlayers(Player player) {
        Bukkit.getOnlinePlayers().forEach(players -> {
            if (!players.getUniqueId().equals(player.getUniqueId()))
                player.hidePlayer(players);
        });
    }

    public static void showToPlayers(Player player) {
        Bukkit.getOnlinePlayers().forEach(players -> {
            if (!players.getUniqueId().equals(player.getUniqueId()))
                players.showPlayer(player);
        });
    }

    public static void showPlayers(Player player) {
        Bukkit.getOnlinePlayers().forEach(players -> {
            if (!players.getUniqueId().equals(player.getUniqueId()))
                player.showPlayer(players);
        });
    }

    public static <T> T getRandomInList(ArrayList<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public static <T> Collection<T> getRandomsInList(ArrayList<T> list, int amount) {
        Set<T> finalList = new HashSet<>();
        for (int i = 0; i < amount; i++) {
            T obj;
            do {
                obj = getRandomInList(list);
            } while (finalList.contains(obj));

            finalList.add(obj);
        }
        return finalList;
    }

    public static String getFormattedTime(int time, ChatColor def) {
        if (time <= 3) return ChatColor.RED + String.valueOf(time);
        else if (time <= 10) return ChatColor.GOLD + String.valueOf(time);
        else return def + String.valueOf(time);
    }
}
