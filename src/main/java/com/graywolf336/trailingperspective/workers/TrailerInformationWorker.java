package com.graywolf336.trailingperspective.workers;

import org.bukkit.ChatColor;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.Util;
import com.graywolf336.trailingperspective.classes.MobTrailer;
import com.graywolf336.trailingperspective.classes.PlayerTrailer;
import com.graywolf336.trailingperspective.enums.Settings;
import com.graywolf336.trailingperspective.interfaces.ITrailer;
import com.graywolf336.trailingperspective.interfaces.ITrailerWorker;
import com.graywolf336.trailingperspective.utilities.ActionBarUtil;

public class TrailerInformationWorker implements ITrailerWorker {
    private TrailingPerspectiveMain pl;
    private Long changeInterval;

    public TrailerInformationWorker(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
        this.changeInterval = Util.getTime(Settings.CHANGE_INTERVAL.asString(), 120000L);
        this.pl.getLogger().info("TrailerInformationWorker started.");
    }

    public void run() {
        for (ITrailer trailer : this.pl.getTrailerManager().getAllTrailers()) {
            if (trailer.isOnline()) {

                StringBuilder msg = new StringBuilder();
                msg.append(ChatColor.DARK_GRAY);
                msg.append("\u2248\u2248 ");
                msg.append(ChatColor.YELLOW);
                msg.append("Trailing: ");
                msg.append(ChatColor.GREEN);
                msg.append(ChatColor.ITALIC);

                if (trailer.isCurrentlyTrailingSomething() && trailer instanceof PlayerTrailer) {
                    msg.append(((PlayerTrailer) trailer).getEntityCurrentlyTrailing().getDisplayName());
                } else if (trailer.isCurrentlyTrailingSomething() && trailer instanceof MobTrailer) {
                    msg.append(Util.getEntityTypeNiceString(trailer.getEntityCurrentlyTrailing().getType()));
                } else {
                    msg.append("N/A");
                }

                msg.append(ChatColor.DARK_GRAY);
                msg.append(" \u2248\u2248 ");
                msg.append(ChatColor.YELLOW);
                msg.append("Time Left: ");
                msg.append(ChatColor.GREEN);
                msg.append(ChatColor.ITALIC);

                if (trailer.isCurrentlyTrailingSomething() && trailer instanceof PlayerTrailer) {
                    long timeLeft = this.changeInterval - trailer.getCurrentPerspectiveTrailingTime();
                    msg.append(Util.getDurationBreakdown(timeLeft));
                } else {
                    msg.append("N/A");
                }
                
                msg.append(ChatColor.DARK_GRAY);
                msg.append(" \u2248\u2248 ");

                ActionBarUtil.sendActionBar(msg.toString(), trailer.getPlayer());
            }
        }
    }
}
