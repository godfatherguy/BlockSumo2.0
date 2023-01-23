package org.godfather.blocksumo.api.utils.messages;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.godfather.blocksumo.api.utils.nms.Reflection;

import java.util.function.BiConsumer;

public enum MessageType implements BiConsumer<Player, String> {

    CHAT, ACTIONBAR;

    @Override
    public void accept(Player player, String s) {
        if (this == CHAT) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
            return;
        }
        Reflection.sendActionbar(player, s);
    }
}
