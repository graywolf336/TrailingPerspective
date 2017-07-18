package com.graywolf336.trailingperspective.commands;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.Util;
import com.graywolf336.trailingperspective.enums.Permissions;
import com.graywolf336.trailingperspective.interfaces.ICommand;

public class PerspectiveBlacklistCommand implements ICommand {
    private TrailingPerspectiveMain pl;

    public PerspectiveBlacklistCommand(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (Permissions.BLACKLIST.check(sender)) {
            //TODO: add, remove, is, list
            switch (args.length) {
                case 1:
                    if (args[1].equalsIgnoreCase("list")) {
                        String blacklist = Util.getStringFromList(", ",
                                pl.getTrailerManager().getBlacklistStream()
                                .map(UUID::fromString).map(Bukkit::getPlayer)
                                .map(Player::getName).collect(Collectors.toList()));
                        
                        sender.sendMessage("The current blacklist contains: " + blacklist);
                    }
                    break;
                case 2:
                    break;
                default:
                    
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "No permission or you're not a player...I'll let you decide. ;)");
        }
        
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
