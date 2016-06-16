package com.graywolf336.trailingperspective.interfaces;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

/**
 * Represents the manager of all the {@link ITrailer ITrailers}.
 *
 * @author graywolf336
 * @since 1.0.0
 * @version 1.0.0
 */
public interface ITrailerManager {
    /**
     * Adds a {@link ITrailer trailer} to manage.
     *
     * @param trailer the trailer to manage
     */
    public void addTrailer(ITrailer trailer);

    /**
     * Removes a {@link ITrailer trailer} from being managed.
     *
     * @param trailer the trailer to unmanage
     * @return whether they were successfully removed or not
     */
    public boolean removeTrailer(ITrailer trailer);

    /**
     * Removes a {@link ITrailer trailer} from being managed by their {@link UUID}.
     *
     * @param uuid the uuid of the trailer to remove
     * @return whether they were successfully removed or not
     */
    public boolean removeTrailer(UUID uuid);

    /**
     * Gets the {@link ITrailer} instance from the player instance.
     *
     * @param player the player to search by.
     * @return the {@link ITrailer} instance
     * @deprecated Use {@link #getTrailer(UUID)}
     */
    @Deprecated
    public ITrailer getTrailer(Player player);

    /**
     * Gets the {@link ITrailer} instance from the uuid provided.
     *
     * @param uuid the uuid to search by.
     * @return the {@link ITrailer} instance
     */
    public ITrailer getTrailer(UUID uuid);

    /**
     * Checks whether the given player instance is a {@link ITrailer trailer} or not.
     *
     * @param player the player to check
     * @return whether the player is a {@link ITrailer trailer} or not
     * @deprecated Use {@link #getTrailer(UUID)}
     */
    @Deprecated
    public boolean isTrailer(Player player);

    /**
     * Checks whether the given {@link UUID} is a {@link ITrailer trailer} or not.
     *
     * @param uuid the uuid to check
     * @return whether the given uuid is a {@link ITrailer trailer} or not
     */
    public boolean isTrailer(UUID uuid);

    /**
     * Checks whether the given player instance is being trailed by a {@link ITrailer trailer} or
     * not.
     *
     * @param player the player to check
     * @return whether the player is being trailed by a {@link ITrailer trailer} or not.
     * @deprecated Use {@link #isBeingTrailed(UUID)}
     */
    @Deprecated
    public boolean isBeingTrailed(Player player);

    /**
     * Checks whether the given {@link UUID} is being trailed by a {@link ITrailer trailer} or not.
     *
     * @param uuid the uuid to check
     * @return whether the uuid is being trailed by a {@link ITrailer trailer} or not.
     */
    public boolean isBeingTrailed(UUID uuid);

    /**
     * Gets the {@link ITrailer trailers} who are currently trailing the given {@link Player}.
     *
     * @param player the player whose trailers we should get
     * @return the {@link ITrailer trailers} trailing the given player
     * @deprecated Use {@link #getTrailersTrailingPlayer(UUID)}
     */
    @Deprecated
    public List<ITrailer> getTrailersTrailingPlayer(Player player);

    /**
     * Gets the {@link ITrailer trailers} who are currently trailing the given {@link UUID}.
     *
     * @param uuid the uuid of the player whose trailers we should get
     * @return the {@link ITrailer trailers} trailing the given player
     */
    public List<ITrailer> getTrailersTrailingPlayer(UUID uuid);

    /**
     * Gets the list of the current {@link ITrailer trailers}
     *
     * @return list of the current trailers
     */
    public List<ITrailer> getTrailers();

    /**
     * Removes all of the current {@link ITrailer trailers} from being managed, only use after
     * removing them from everything else.
     *
     * @return the list of removed trailers
     */
    public List<ITrailer> removeAllTrailers();
}
