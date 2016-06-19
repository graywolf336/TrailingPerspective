package com.graywolf336.trailingperspective.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.graywolf336.trailingperspective.classes.MobTrailer;

/**
 * Event thrown when a player turned into a mob trailer.
 *
 * @author graywolf336
 * @since 1.0.0
 * @version 1.0.0
 */
public class NewMobTrailerEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private MobTrailer trailer;

    /**
     * Creates a new {@link NewMobTrailerEvent new mob trailer event} for the given player.
     *
     * @param player the {@link Player} involved
     * @param trailer the {@link MobTrailer} instance
     */
    public NewMobTrailerEvent(Player player, MobTrailer trailer) {
        this.player = player;
        this.trailer = trailer;
    }

    /**
     * Gets the {@link Player player} who is the new trailer.
     *
     * @return the player who is the trailer
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Gets the {@link MobTrailer} instance for the new trailer.
     *
     * @return the trailer instance
     */
    public MobTrailer getTrailer() {
        return this.trailer;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
