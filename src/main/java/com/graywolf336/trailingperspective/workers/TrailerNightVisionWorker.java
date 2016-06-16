package com.graywolf336.trailingperspective.workers;

import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.Util;
import com.graywolf336.trailingperspective.enums.Settings;
import com.graywolf336.trailingperspective.interfaces.ITrailer;
import com.graywolf336.trailingperspective.interfaces.ITrailerWorker;

public class TrailerNightVisionWorker implements ITrailerWorker {
    private TrailingPerspectiveMain pl;
    private PotionEffect eff;

    public TrailerNightVisionWorker(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
        this.eff = new PotionEffect(PotionEffectType.NIGHT_VISION, Settings.NIGHT_VISION_POTION_LENGTH_SECONDS.asInt() * Util.TICKS_PER_SECOND, 0, false, false);
        this.pl.getLogger().info("TrailerNightVisionWorker started.");
    }

    public void run() {
        for (ITrailer trailer : this.pl.getTrailerManager().getAllTrailers()) {
            if (trailer.isOnline()) {
                Player p = trailer.getPlayer();
                Location l = p.getEyeLocation();
                if (l.getWorld().getEnvironment() == Environment.NORMAL && l.getBlock().getLightLevel() <= Settings.LIGHT_LEVEL_FOR_NIGHT_VISION.asByte()) {
                    p.addPotionEffect(eff, true);
                }
            }
        }
    }
}
