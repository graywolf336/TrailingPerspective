package com.graywolf336.trailingperspective.interfaces;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

/**
 * Represents a trailer who is being managed and shown perspectives.
 *
 * @author graywolf336
 * @since 1.0.0
 * @version 1.0.0
 */
public interface ITrailer {
    /**
     * Gets the {@link Player} who is this trailer.
     *
     * @return the {@link Player} who this is
     */
    public Player getPlayer();

    /**
     * Gets the {@link UUID} of this trailer which directly correlates to the {@link Player}.
     *
     * @return the {@link UUID} of this trailer
     */
    public UUID getUUID();

    /**
     * Gets a cached copy of the trailer's username.
     *
     * @return the cached copy of the trailer's username.
     */
    public String getUsername();

    /**
     * Checks whether the {@link Player} is online or not.
     *
     * @return whether the {@link Player} is online or not.
     */
    public boolean isOnline();

    /**
     * Checks whether this trailer was sent home already or not.
     *
     * @return whether the trailer was sent home already
     */
    public boolean wasSentHome();

    /**
     * Sets whether this trailer has been sent home.
     *
     * @param sent whether they're sent home or not
     */
    public void setWhetherTheyHaveBeenSentHome(boolean sent);

    /**
     * Gets the amount of time this trailer has been trailing perspectives.
     *
     * @return the amount of time in milliseconds this person has been a trailer
     */
    public Long getTotalTimeTrailing();

    /**
     * Gets whether this trailer is currently trailing someone or not.
     *
     * @return whether this trailing is busy trailing someone or not
     */
    public boolean isCurrentlyTrailingSomeone();

    /**
     * Gets the amount of time this trailer has been trailing the current perspective.
     *
     * @return the amount of time in milliseconds this person has been trailing this particular
     *         perspective.
     */
    public Long getCurrentPerspectiveTrailingTime();

    /**
     * Sets the {@link Player} this trailer is currently trailing.
     *
     * @param player the player who they're trailing.
     */
    public void setPlayerCurrentlyTrailing(Player player);

    /**
     * Gets the {@link Player} this trailer is currently viewing the perspective of.
     *
     * @return the current trailer's perspective's {@link Player}
     */
    public Player getPlayerCurrentlyTrailing();

    /**
     * Gets the {@link UUID} of the player who this trailer is currently viewing the perspective of.
     *
     * @return the {@link UUID} of the current trailer's perspective
     */
    public UUID getUUIDOfPlayerCurrentlyTrailing();

    /** Sets that this trailer is no longer trailing anyone. */
    public void setNoLongerTrailingAnyone();
    
    /** Flags that this trailer is ready to switch perspectives. */
    public void flagReadyToGoNext();

    /**
     * Gets a list of the usernames who this trailer has trailed.
     *
     * @return a string list of the usernames who this trailer trailed
     * @deprecated TODO: Make this a solid method that won't change again
     */
    @Deprecated
    public List<String> getPlayersLastTrailed();
}
