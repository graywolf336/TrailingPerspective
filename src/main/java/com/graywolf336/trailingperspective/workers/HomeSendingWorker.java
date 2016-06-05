package com.graywolf336.trailingperspective.workers;

import org.bukkit.ChatColor;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.enums.Settings;
import com.graywolf336.trailingperspective.interfaces.ITrailer;
import com.graywolf336.trailingperspective.interfaces.ITrailerWorker;

public class HomeSendingWorker implements ITrailerWorker {
    private TrailingPerspectiveMain pl;
    
    public HomeSendingWorker(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
        this.pl.getLogger().info("HomeSendingWorker started.");
    }

    public void run() {
        if(Settings.HOME_LOCATION.asLocation() == null) return;
        
        for(ITrailer trailer : this.pl.getTrailerManager().getTrailers()) {
            if(trailer.isOnline() && !trailer.isCurrentlyTrailingSomeone() && !trailer.wasSentHome()) {
                trailer.getPlayer().teleport(Settings.HOME_LOCATION.asLocation());
                trailer.setWhetherTheyHaveBeenSentHome(true);
                
                trailer.getPlayer().sendMessage(ChatColor.AQUA + "You've been sent home as there is no one to trail currently.");
            }
        }
    }
}
