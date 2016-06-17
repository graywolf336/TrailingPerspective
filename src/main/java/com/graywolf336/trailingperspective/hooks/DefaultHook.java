package com.graywolf336.trailingperspective.hooks;

import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.classes.Hook;

public class DefaultHook extends Hook {
    public String getName() {
        return "Default";
    }

    public boolean isPlayerOkayToBeTrailed(Player player) {
        return !player.isDead() && !player.isSleeping() && !player.isInsideVehicle();
    }
}
