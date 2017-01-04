package com.graywolf336.trailingperspective.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.enums.Permissions;
import com.graywolf336.trailingperspective.enums.Settings;
import com.graywolf336.trailingperspective.interfaces.ICommand;

public class SetTrailerHomeCommand implements ICommand {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (Permissions.SET_TRAILER_HOME.check(sender) && sender instanceof Player) {
            Player player = (Player) sender;
            Settings.HOME_LOCATION.setAndSave(player.getLocation());
            player.sendMessage(ChatColor.GREEN + "Trailer home location successfully set!");
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "No permission or you're not a player...I'll let you decided. ;)");
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
