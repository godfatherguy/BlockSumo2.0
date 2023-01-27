package org.godfather.blocksumo.api.game;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.godfather.blocksumo.api.utils.Utils;

import java.util.List;

public interface GameDescription {

    String getMinigameName();

    List<String> getMinigameDescription();

    Material getGUIIcon();

    default List<String> getStartMessage() {
        List<String> message = Lists.newArrayList();
        message.add("");
        message.add(Utils.centeredMessage(ChatColor.WHITE + "" + ChatColor.BOLD + getMinigameName()));
        message.add("");
        getMinigameDescription().forEach(line -> message.add(Utils.centeredMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + line)));
        message.add("");
        return message;
    }
}
