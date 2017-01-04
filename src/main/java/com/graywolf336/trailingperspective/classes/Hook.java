package com.graywolf336.trailingperspective.classes;

import org.bukkit.entity.Player;

/**
 * Represents a Hook into another plugin.
 *
 * @author graywolf336
 * @since 1.0.0
 * @version 1.0.0
 */
public abstract class Hook {
    /**
     * Gets the name of this hook.
     *
     * @return name of the hook
     */
    public abstract String getName();

    /**
     * Checks if the {@link Player} is okay to be trailed.
     *
     * @param player the player to be checked
     * @return whether the player is ready to be trailed
     */
    public boolean isPlayerOkayToBeTrailed(Player player) {
        return true;
    }
}
