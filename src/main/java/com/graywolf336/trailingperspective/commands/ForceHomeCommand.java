package com.graywolf336.trailingperspective.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.classes.Trailer;
import com.graywolf336.trailingperspective.enums.Permissions;
import com.graywolf336.trailingperspective.interfaces.ICommand;

public class ForceHomeCommand implements ICommand {
    private TrailingPerspectiveMain pl;

    public ForceHomeCommand(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (Permissions.SET_FORCE_HOME.has(sender) && sender instanceof Player) {
            Trailer trailer = (Trailer) pl.getTrailerManager().getTrailer(player.getUniqueId());

            if (trailer.isForcedHome()) {
                trailer.forcePlayerHome();
                player.sendMessage(ChatColor.GREEN + "Trailer forced home.");
            } else {
                trailer.unForcePlayerHome();
                player.sendMessage(ChatColor.GREEN + "Trailer unforced from home.");
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "No permission or you're not a player...I'll let you decide. ;)");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }

}
