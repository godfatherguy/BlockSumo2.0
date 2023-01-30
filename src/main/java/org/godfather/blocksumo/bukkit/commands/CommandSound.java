package org.godfather.blocksumo.bukkit.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSound implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length != 3) {
            sender.sendMessage("§cUtilizza: /sound <suono> <volume> <tonalità>");
            return false;
        }

        Sound sound = Sound.valueOf(args[0].toUpperCase());

        float volume = 1, pitch = 1;
        try {
            volume = Float.parseFloat(args[1]);
            pitch = Float.parseFloat(args[2]);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        Player player = (Player) sender;
        player.playSound(player.getLocation(), sound, volume, pitch);
        return true;
    }
}
