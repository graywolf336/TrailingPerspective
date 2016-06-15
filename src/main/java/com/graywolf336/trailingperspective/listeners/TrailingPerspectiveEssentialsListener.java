package com.graywolf336.trailingperspective.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;

import net.ess3.api.IUser;
import net.ess3.api.events.AfkStatusChangeEvent;

public class TrailingPerspectiveEssentialsListener implements Listener {
    private TrailingPerspectiveMain pl;

    public TrailingPerspectiveEssentialsListener(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
    }
    
    @EventHandler
    public void playerHasGoneAfk(AfkStatusChangeEvent event) {
        IUser user = event.getAffected();

        if(user.isAfk() && this.pl.getTrailerManager().isBeingTrailed(user.getBase().getUniqueId())) {
            pl.getTrailerManager().getTrailersTrailingPlayer(user.getBase().getUniqueId()).forEach(t -> t.setNoLongerTrailingAnyone());
    	}
    }
}
