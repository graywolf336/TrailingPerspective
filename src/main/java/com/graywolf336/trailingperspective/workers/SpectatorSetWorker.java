package com.graywolf336.trailingperspective.workers;

import org.bukkit.GameMode;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.interfaces.ITrailer;
import com.graywolf336.trailingperspective.interfaces.ITrailerWorker;

public class SpectatorSetWorker implements ITrailerWorker {
    private TrailingPerspectiveMain pl;

    public SpectatorSetWorker(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
        this.pl.getLogger().info("SpectatorSetWorker started.");
    }

    public void run() {
        for (ITrailer trailer : pl.getTrailerManager().getTrailers()) {
            if (trailer.isOnline() && trailer.getPlayer().getGameMode() != GameMode.SPECTATOR) {
                trailer.getPlayer().setGameMode(GameMode.SPECTATOR);
                pl.debug(false, "Updated " + trailer.getPlayer().getName() + "'s GameMode to spectator.");
            }
        }
    }
}
