package com.graywolf336.trailingperspective.workers;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.interfaces.ITrailer;
import com.graywolf336.trailingperspective.interfaces.ITrailerWorker;

public class MobTrailersPerspectiveWorker implements ITrailerWorker {
    private TrailingPerspectiveMain pl;

    public MobTrailersPerspectiveWorker(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
        this.pl.getLogger().info("MobTrailersPerspectiveWorker started.");
    }

    public void run() {
        //Loop through all of the MobTrailers
        for(ITrailer trailer : pl.getTrailerManager().getMobTrailers()) {
            if (trailer.isOnline() && !trailer.isForcedHome() && trailer.getPlayer().getGameMode() == GameMode.SPECTATOR) {
                //If they don't have an entity currently assigned/trailing them,
                //then ignore them
                if (trailer.getEntityCurrentlyTrailing() == null) {
                    continue;
                }

                Entity e = trailer.getEntityCurrentlyTrailing();
                
                if (e.isDead()) {
                    //TODO: Determine if there is something different we should do
                    trailer.setNoLongerTrailingAnything();
                    continue;
                }
                
                if (trailer.getPlayer().getSpectatorTarget() != null &&
                        e.getUniqueId().equals(trailer.getPlayer().getSpectatorTarget().getUniqueId())) {
                    continue;
                }

                trailer.getPlayer().setSpectatorTarget(e);
            }
        }
    }
}
