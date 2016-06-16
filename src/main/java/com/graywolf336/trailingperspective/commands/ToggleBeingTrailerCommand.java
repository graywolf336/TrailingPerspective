package com.graywolf336.trailingperspective.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.classes.PlayerTrailer;
import com.graywolf336.trailingperspective.enums.Permissions;
import com.graywolf336.trailingperspective.interfaces.ICommand;

public class ToggleBeingTrailerCommand implements ICommand {
    private TrailingPerspectiveMain pl;

    public ToggleBeingTrailerCommand(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (Permissions.TOGGLE_BEING_TRAILER.has(sender) && sender instanceof Player) {
            Player player = (Player) sender;

            if (pl.getTrailerManager().isTrailer(player.getUniqueId())) {
                pl.getTrailerManager().removeTrailer(player.getUniqueId());
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(ChatColor.RED + "You're no longer a trailer of other's perspectives.");
            } else {
                pl.getTrailerManager().addTrailer(new PlayerTrailer(player));
                player.sendMessage(ChatColor.GREEN + "You're now a trailer of other's perspectives.");
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "No permission or you're not a player...I'll let you decide. ;)");
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
