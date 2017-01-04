package com.graywolf336.trailingperspective.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.classes.MobTrailer;
import com.graywolf336.trailingperspective.enums.Permissions;
import com.graywolf336.trailingperspective.interfaces.ICommand;

public class ToggleBeingMobTrailerCommand implements ICommand {
    private TrailingPerspectiveMain pl;

    public ToggleBeingMobTrailerCommand(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (Permissions.TOGGLE_BEING_MOB_TRAILER.check(sender) && sender instanceof Player) {
            Player player = (Player) sender;

            if (pl.getTrailerManager().isMobTrailer(player.getUniqueId())) {
                pl.getTrailerManager().removeTrailer(player.getUniqueId());
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(ChatColor.RED + "You're no longer a trailer of a mob.");
            } else {
                LivingEntity target = this.getEntityPlayerIsLookingAt(player);
                
                if (target == null) {
                    player.sendMessage(ChatColor.GRAY + "*cough* " + ChatColor.RED + "You aren't looking at a LivingEntity (aka a mob)!");
                } else {
                    pl.getTrailerManager().addTrailer(new MobTrailer(player, target));
                    player.sendMessage(ChatColor.GREEN + "You're now a trailer of a mob's perspective.");
                }
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "No permission or you're not a player...I'll let you decide. ;)");
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
    
    private LivingEntity getEntityPlayerIsLookingAt(Player player) {
        for (Entity e : player.getNearbyEntities(32, 48, 32)) {
            if (!(e instanceof LivingEntity) || e instanceof Player) continue;
            
            LivingEntity le = (LivingEntity) e;
            if (this.isLookingAt(player, le)) return le; 
        }
        
        return null;
    }
    
    private boolean isLookingAt(Player player, LivingEntity livingEntity) {
        Vector toEntity = livingEntity.getEyeLocation().toVector().subtract(player.getEyeLocation().toVector());
        double dot = toEntity.normalize().dot(player.getEyeLocation().getDirection());

        return dot > 0.99D;
    }
}
