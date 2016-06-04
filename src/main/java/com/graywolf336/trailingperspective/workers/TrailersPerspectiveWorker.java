package com.graywolf336.trailingperspective.workers;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.Util;
import com.graywolf336.trailingperspective.enums.Settings;
import com.graywolf336.trailingperspective.interfaces.ITrailer;
import com.graywolf336.trailingperspective.interfaces.ITrailerWorker;

public class TrailersPerspectiveWorker implements ITrailerWorker {
    private TrailingPerspectiveMain pl;
    private Long changeInterval;

    public TrailersPerspectiveWorker(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
        this.changeInterval = Util.getTime(Settings.CHANGE_INTERVAL.asString(), 120000L);
        this.pl.getLogger().info("TrailersPerspectiveWorker started with a change internval of " + this.changeInterval + " milliseconds.");
    }

    @SuppressWarnings("deprecation")
    public void run() {
        if(this.pl.getServer().getOnlinePlayers().size() <= this.pl.getTrailerManager().getTrailers().size()) return;
        
        for (ITrailer trailer : this.pl.getTrailerManager().getTrailers()) {
            if (trailer.isOnline() && trailer.getPlayer().getGameMode() == GameMode.SPECTATOR && (trailer.getCurrentPerspectiveTrailingTime() > this.changeInterval || trailer.getCurrentPerspectiveTrailingTime() == 0)) {
                Player p = null;

                // Check if they have trailed someone yet or not
                if (trailer.getPlayersLastTrailed().isEmpty()) {
                    p = Util.getRandomPlayerNotInList(trailer.getPlayer().getName());
                } else {
                    String lastUser = trailer.getPlayersLastTrailed().get(trailer.getPlayersLastTrailed().size() - 1);
                    p = Util.getRandomPlayerNotInList(trailer.getPlayer().getName(), lastUser);
                    
                    //if we couldn't get anyone, then just get someone who isn't this trailer
                    if (p == null) {
                        p = Util.getRandomPlayerNotInList(trailer.getPlayer().getName());
                    }
                    
                    //If the player isn't null and it's the same player as who was before,
                    //then don't try anything fancy and just move onto the next trailer
                    if(p != null && p.getName().equalsIgnoreCase(lastUser)) {
                        continue;
                    }
                }

                if (p == null) {
                    this.pl.getLogger().warning("Failed to get a player to trail for " + trailer.getPlayer().getName());
                } else {
                    trailer.setPlayerCurrentlyTrailing(p);
                    trailer.getPlayer().teleport(p);
                    trailer.getPlayer().setSpectatorTarget(null);
                    
                    final Player target = p;
                    this.pl.getServer().getScheduler().runTaskLater(this.pl, () -> {
                        trailer.getPlayer().setSpectatorTarget(target);
                    }, 2L);

                    this.pl.getLogger().info(trailer.getUsername() + " is now trailing the perspective of " + p.getName());
                    trailer.getPlayer().sendMessage(ChatColor.GREEN + "NOW TRAILING: " + p.getDisplayName());
                    trailer.getPlayer().sendTitle(ChatColor.GREEN + "Spectating: " + p.getDisplayName(), "");
                }
            }
        }
    }
}
