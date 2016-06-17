package com.graywolf336.trailingperspective.interfaces;

import java.util.List;

import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.classes.Hook;

/**
 * Represents the manager of all the {@link Hook Hooks}.
 *
 * @author graywolf336
 * @since 1.0.0
 * @version 1.0.0
 */
public interface IHookManager {
    /**
     * Gets a list of all the {@link Hook Hooks} registered.
     *
     * @return list of the hooks
     */
    public List<Hook> getHooks();

    /**
     * Gets the specific {@link Hook} by the given name.
     *
     * @param name of the hook to get
     * @return the {@link Hook} by that name or null
     */
    public Hook getHook(String name);

    /**
     * Adds the provided {@link Hook} into the system.
     *
     * @param hook the {@link Hook} to register
     */
    public void addHook(Hook hook);

    /**
     * Method to run all the checks on the {@link Hook Hooks} to see if a player can be spectated.
     *
     * @param player the {@link Player} to check against
     * @return whether the player can be trailed or not
     */
    public boolean checkHooksToSeeIfPlayerIsOkayToBeTrailed(Player player);
}
