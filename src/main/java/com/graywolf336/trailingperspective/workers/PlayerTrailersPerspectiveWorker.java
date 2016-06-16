package com.graywolf336.trailingperspective.workers;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.Util;
import com.graywolf336.trailingperspective.enums.Settings;
import com.graywolf336.trailingperspective.interfaces.ITrailer;
import com.graywolf336.trailingperspective.interfaces.ITrailerWorker;

public class PlayerTrailersPerspectiveWorker implements ITrailerWorker {
    private TrailingPerspectiveMain pl;
    private Long changeInterval;

    public PlayerTrailersPerspectiveWorker(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
        this.changeInterval = Util.getTime(Settings.CHANGE_INTERVAL.asString(), 120000L);
        this.pl.getLogger().info("PlayerTrailersPerspectiveWorker started with a change internval of " + this.changeInterval + " milliseconds.");
    }

    @SuppressWarnings("deprecation")
    public void run() {
        if (this.pl.getServer().getOnlinePlayers().size() <= this.pl.getTrailerManager().getPlayerTrailers().size())
            return;

        ArrayList<ITrailer> trailers = new ArrayList<ITrailer>(this.pl.getTrailerManager().getPlayerTrailers());
        for (ITrailer trailer : trailers) {
            if (trailer.isOnline() && !trailer.isForcedHome() && trailer.getPlayer().getGameMode() == GameMode.SPECTATOR && (trailer.getCurrentPerspectiveTrailingTime() > this.changeInterval || trailer.getCurrentPerspectiveTrailingTime() == 0)) {
                Player p = null;

                // Check if they have trailed someone yet or not
                if (trailer.getEntitiesLastTrailed().isEmpty()) {
                    p = Util.getRandomAlivePlayerNotInList(trailers);
                } else {
                    String lastUser = trailer.getEntitiesLastTrailed().get(trailer.getEntitiesLastTrailed().size() - 1);
                    p = Util.getRandomAlivePlayerNotInList(trailers, lastUser);

                    // if we couldn't get anyone, then just get someone who isn't this trailer
                    if (p == null) {
                        p = Util.getRandomAlivePlayerNotInList(trailers);
                    }

                    // If the player isn't null and it's the same player as who was before,
                    // then don't try anything fancy and just move onto the next trailer
                    if (p != null && p.getName().equalsIgnoreCase(lastUser) && trailer.getCurrentPerspectiveTrailingTime() != 0) {
                        continue;
                    }
                }

                if (p == null) {
                    trailer.setNoLongerTrailingAnything();
                } else {
                    trailer.getPlayer().setSpectatorTarget(null);
                    trailer.setEntityCurrentlyTrailing(p);

                    final Player target = p;
                    this.pl.getServer().getScheduler().runTaskLater(this.pl, () -> trailer.getPlayer().teleport(target), 1L);
                    this.pl.getServer().getScheduler().runTaskLater(this.pl, () -> trailer.getPlayer().setSpectatorTarget(target), 3L);

                    this.pl.getLogger().info(trailer.getUsername() + " is now trailing the perspective of " + p.getName());
                    trailer.getPlayer().sendTitle("", ChatColor.GREEN + "Now Trailing: " + p.getDisplayName());
                }
            }
        }
    }
}
