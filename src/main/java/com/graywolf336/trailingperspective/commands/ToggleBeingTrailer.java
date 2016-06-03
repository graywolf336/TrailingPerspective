package com.graywolf336.trailingperspective.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.classes.Trailer;
import com.graywolf336.trailingperspective.interfaces.ICommand;

public class ToggleBeingTrailer implements ICommand {
    private TrailingPerspectiveMain pl;

    public ToggleBeingTrailer(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("trailingperspective.togglebeingtrailer") && sender instanceof Player) {
            Player player = (Player) sender;

            if (pl.getTrailerManager().isTrailer(player.getUniqueId())) {
                pl.getTrailerManager().removeTrailer(player.getUniqueId());
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(ChatColor.RED + "You're no longer a trailer of other's perspectives.");
            } else {
                pl.getTrailerManager().addTrailer(new Trailer(player));
                player.sendMessage(ChatColor.GREEN + "You're now a trailer of other's perspectives.");
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "No permission or you're not a player...I'll let you decided. ;)");
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
