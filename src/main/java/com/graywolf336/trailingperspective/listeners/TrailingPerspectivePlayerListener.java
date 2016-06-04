package com.graywolf336.trailingperspective.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.classes.Trailer;
import com.graywolf336.trailingperspective.enums.Settings;

public class TrailingPerspectivePlayerListener implements Listener {
    private TrailingPerspectiveMain pl;

    public TrailingPerspectivePlayerListener(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
    }

    @EventHandler
    public void playerHasDecidedToJoinUs(PlayerJoinEvent event) {
        if (Settings.USERNAME.asString().equalsIgnoreCase(event.getPlayer().getName()) && Settings.AUTOMATICALLY_ENABLED.asBoolean()) {
            this.pl.debug(false, "The selected player has joined the server and automatically enabled is set to true!");
            this.pl.getTrailerManager().addTrailer(new Trailer(event.getPlayer()));
        }
    }

    @EventHandler
    public void playerHasDecidedToLeaveUs(PlayerQuitEvent event) {
        playerLeft(event.getPlayer());
    }
    
    @EventHandler
    public void playerWasForcefullyRemovedFromUs(PlayerKickEvent event) {
        playerLeft(event.getPlayer());
    }
    
    private void playerLeft(Player player) {
        if (this.pl.getTrailerManager().isTrailer(player.getUniqueId())) {
            this.pl.debug(false, "A trailing player has left us.");
            this.pl.getTrailerManager().removeTrailer(player.getUniqueId());
            
            if(player.getGameMode() == GameMode.SPECTATOR) {
                player.setSpectatorTarget(null);
            }
        }else if (this.pl.getTrailerManager().isBeingTrailed(player.getUniqueId())) {
            this.pl.debug(false, "A person who is being trailed has left us.");
            
            pl.getTrailerManager().getTrailersTrailingPlayer(player.getUniqueId()).forEach(t -> t.setNoLongerTrailingAnyone());
        }
    }
}
