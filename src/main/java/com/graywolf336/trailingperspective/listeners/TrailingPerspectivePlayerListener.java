package com.graywolf336.trailingperspective.listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.classes.Trailer;
import com.graywolf336.trailingperspective.enums.Settings;
import com.graywolf336.trailingperspective.interfaces.ITrailer;

public class TrailingPerspectivePlayerListener implements Listener {
    private TrailingPerspectiveMain pl;

    public TrailingPerspectivePlayerListener(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
    }

    @EventHandler
    public void playersAreChattyPeople(AsyncPlayerChatEvent event) {
        if (Settings.CLEAR_TRAILERS_CHAT.asBoolean()) {
            Set<Player> rec = new HashSet<Player>(event.getRecipients());

            for (ITrailer trailer : this.pl.getTrailerManager().getTrailers()) {
                rec.remove(trailer.getPlayer());
            }

            event.getRecipients().clear();
            event.getRecipients().addAll(rec);
        }
    }

    @EventHandler
    public void playerDoneDied(PlayerDeathEvent event) {
        if (this.pl.getTrailerManager().isBeingTrailed(event.getEntity().getUniqueId())) {
            pl.getTrailerManager().getTrailersTrailingPlayer(event.getEntity().getUniqueId()).forEach(t -> t.setNoLongerTrailingAnyone());
        }
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

            if (player.getGameMode() == GameMode.SPECTATOR) {
                player.setSpectatorTarget(null);
            }
        } else if (this.pl.getTrailerManager().isBeingTrailed(player.getUniqueId())) {
            this.pl.debug(false, "A person who is being trailed has left us.");

            pl.getTrailerManager().getTrailersTrailingPlayer(player.getUniqueId()).forEach(t -> t.setNoLongerTrailingAnyone());
        }
    }
}
