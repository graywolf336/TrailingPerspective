package com.graywolf336.trailingperspective.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.graywolf336.trailingperspective.classes.PlayerTrailer;

/**
 * Event thrown when a player trailer has their perspective changed.
 * 
 * @author graywolf336
 * @since 1.0.0
 * @version 1.0.0
 */
public class PlayerTrailerPerspectiveChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private PlayerTrailer trailer;
    private Player perspective;
    
    /**
     * Creates a new {@link PlayerTrailerPerspectiveChangeEvent player trailer perspective change event} for the given trailer and perspective.
     * 
     * @param trailer
     * @param trailed
     */
    public PlayerTrailerPerspectiveChangeEvent(PlayerTrailer trailer, Player perspective) {
        this.trailer = trailer;
        this.perspective = perspective;
    }
    
    /**
     * Gets the {@link Player} who is the trailer.
     * 
     * @return the player who is the trailer
     */
    public Player getTrailerPlayer() {
        return this.trailer.getPlayer();
    }
    
    /**
     * Gets the {@link PlayerTrailer} for this trailer.
     * 
     * @return
     */
    public PlayerTrailer getTrailer() {
        return this.trailer;
    }
    
    /**
     * Gets the {@link Player} whose perspective the trailer is now viewing.
     * 
     * @return the player who is the perspective
     */
    public Player getPerspective() {
        return this.perspective;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
