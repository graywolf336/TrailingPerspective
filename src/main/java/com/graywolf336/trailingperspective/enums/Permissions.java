package com.graywolf336.trailingperspective.enums;

import org.bukkit.command.CommandSender;

/**
 * Represents the permissions used in the Trailing Perspective plugin.
 *
 * @author graywolf336
 * @since 1.0.0
 * @version 1.0.0
 */
public enum Permissions {
    /** The permission for the toggle being a trailer command, defaults to op. */
    TOGGLE_BEING_TRAILER("trailingperspective.togglebeingtrailer"),
    /** The permission for being able to set the trailer's home location, defaults to op. */
    SET_TRAILER_HOME("trailingperspective.settrailerhome"),
    /**
     * The permission for being able to manually trigger the switch to the next perspective for
     * <strong>yourself</strong>, defaults to op.
     */
    SWITCH_PERSPECTIVE("trailingperspective.switchperspective"),
    /**
     * The permission for being able to manually trigger the switch to the next perspective for
     * <strong>others</strong>, defaults to op.
     */
    SWITCH_PERSPECTIVE_OTHERS("trailingperspective.switchperspective.others"),
    /**
     * The permission for being able to force the trailer home, defaults to op.
     */
    SET_FORCE_HOME("trailingperspective.setforcehome");

    private String node;

    private Permissions(String node) {
        this.node = node;
    }

    /**
     * Gets the permission node for this permission.
     *
     * @return the node
     */
    public String getNode() {
        return this.node;
    }

    /**
     * Checks if the given sender, can be a player, has the permission.
     *
     * @param sender the person to check
     * @return whether the player has permission or not
     */
    public boolean check(CommandSender sender) {
        return sender.hasPermission(this.node);
    }
}
