package org.godfather.blocksumo.api.server.tablist;

import org.bukkit.entity.Player;

import java.util.List;

public interface Tablist {

    List<String> getTablistHeader(Player player);

    List<String> getTablistFooter(Player player);
}
