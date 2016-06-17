package com.graywolf336.trailingperspective.hooks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.earth2me.essentials.Essentials;
import com.graywolf336.trailingperspective.TrailingPerspectiveMain;
import com.graywolf336.trailingperspective.classes.Hook;

import net.ess3.api.IUser;
import net.ess3.api.events.AfkStatusChangeEvent;

public class EssentialsHook extends Hook implements Listener {
    private TrailingPerspectiveMain pl;
    private Essentials ess;

    public EssentialsHook(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
        this.ess = (Essentials) plugin.getServer().getPluginManager().getPlugin("Essentials");
    }

    public String getName() {
        return "Essentials";
    }

    public boolean isPlayerOkayToBeTrailed(Player player) {
        return !this.ess.getUser(player).isAfk();
    }

    @EventHandler
    public void playerHasGoneAfk(AfkStatusChangeEvent event) {
        IUser user = event.getAffected();

        if (user.isAfk() && this.pl.getTrailerManager().isBeingTrailed(user.getBase().getUniqueId())) {
            pl.getTrailerManager().getTrailersTrailingPlayer(user.getBase().getUniqueId()).forEach(t -> t.setNoLongerTrailingAnyone());
        }
    }
}
