package com.graywolf336.trailingperspective.workers;

import org.bukkit.GameMode;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.interfaces.ITrailer;
import com.graywolf336.trailingperspective.interfaces.ITrailerWorker;

public class SpectatorSetGameModeWorker implements ITrailerWorker {
    private TrailingPerspectiveMain pl;

    public SpectatorSetGameModeWorker(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
        this.pl.getLogger().info("SpectatorSetGameModeWorker started.");
    }

    public void run() {
        for (ITrailer trailer : pl.getTrailerManager().getAllTrailers()) {
            if (trailer.isOnline() && trailer.getPlayer().getGameMode() != GameMode.SPECTATOR) {
                trailer.getPlayer().setGameMode(GameMode.SPECTATOR);
                pl.debug(false, "Updated " + trailer.getPlayer().getName() + "'s GameMode to spectator.");
            }
        }
    }
}
