package com.graywolf336.trailingperspective.hooks;

import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.classes.Hook;
import com.graywolf336.trailingperspective.interfaces.ITrailerManager;

public class BlacklistHook extends Hook {
    private static final String name = "Blacklist";
    private ITrailerManager trailerManager;
    
    public BlacklistHook(ITrailerManager trailerManager) {
        this.trailerManager = trailerManager;
    }

    public String getName() {
        return name;
    }
    
    public boolean isPlayerOkayToBeTrailed(Player player) {
        return !this.trailerManager.isBlacklisted(player.getUniqueId());
    }
}
