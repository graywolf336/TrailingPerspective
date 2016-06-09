package com.graywolf336.trailingperspective.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.enums.Permissions;
import com.graywolf336.trailingperspective.interfaces.ICommand;
import com.graywolf336.trailingperspective.interfaces.ITrailer;

public class SwitchPerspectiveCommand implements ICommand {
    private TrailingPerspectiveMain pl;

    public SwitchPerspectiveCommand(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            switch (args.length) {
                case 0:
                    Player p = (Player) sender;
                    if (!Permissions.SWITCH_PERSPECTIVE.has(p)) {
                        this.noPermissionsMessage(p);
                        break;
                    }

                    if (!this.pl.getTrailerManager().isTrailer(p.getUniqueId())) {
                        p.sendMessage(ChatColor.RED + "You're currently not a trailer, so we can't switch your perspective.");
                        break;
                    }

                    this.pl.getTrailerManager().getTrailer(p.getUniqueId()).flagReadyToGoNext();
                    p.sendMessage(ChatColor.GREEN + "Your perspecitve will be switched here shortly.");
                    break;
                case 1:
                    this.handleSwitchingOthers(sender, args[0]);
                    break;
                default:
                    sender.sendMessage(ChatColor.DARK_RED + "HUH!?");
                    break;
            }
        } else {
            if (args.length == 1) {
                this.handleSwitchingOthers(sender, args[0]);
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "Who exactly do you want to have their perspectives switched?!");
            }
        }

        return true;
    }

    private void handleSwitchingOthers(CommandSender sender, String name) {
        if (!Permissions.SWITCH_PERSPECTIVE_OTHERS.has(sender)) {
            this.noPermissionsMessage(sender);
            return;
        }

        @SuppressWarnings("deprecation")
        Player player = pl.getServer().getPlayer(name);

        if (player == null) {
            sender.sendMessage(ChatColor.RED + "No player found by the name of \"" + name + "\" to switch the perspective of.");
            return;
        }

        if (!this.pl.getTrailerManager().isTrailer(player.getUniqueId())) {
            sender.sendMessage(ChatColor.RED + "The player \"" + player.getName() + "\" is not currently a trailer.");
            return;
        }

        this.pl.getTrailerManager().getTrailer(player.getUniqueId()).flagReadyToGoNext();
        sender.sendMessage(ChatColor.GREEN + "\"" + player.getName() + "\"'s perspecitve will be switched here shortly.");
    }

    private void noPermissionsMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_RED + "No permission or you're not a player...I'll let you decided. ;)");
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (Permissions.SWITCH_PERSPECTIVE_OTHERS.has(sender)) {
            List<String> results = new ArrayList<String>();
            for (ITrailer t : this.pl.getTrailerManager().getTrailers()) {
                if (StringUtil.startsWithIgnoreCase(t.getUsername(), args[0].toLowerCase())) {
                    results.add(t.getUsername());
                }
            }

            Collections.sort(results);
            return results;
        }

        return Collections.emptyList();
    }
}
