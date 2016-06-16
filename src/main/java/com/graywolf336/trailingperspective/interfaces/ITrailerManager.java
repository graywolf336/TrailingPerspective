package com.graywolf336.trailingperspective.interfaces;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.classes.MobTrailer;
import com.graywolf336.trailingperspective.classes.PlayerTrailer;

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
     * @param entity the entity to check
     * @return whether the player is being trailed by a {@link ITrailer trailer} or not.
     * @deprecated Use {@link #isBeingTrailed(UUID)}
     */
    @Deprecated
    public boolean isBeingTrailed(Entity entity);

    /**
     * Checks whether the given {@link UUID} is being trailed by a {@link ITrailer trailer} or not.
     *
     * @param uuid the uuid to check
     * @return whether the uuid is being trailed by a {@link ITrailer trailer} or not.
     */
    public boolean isBeingTrailed(UUID uuid);

    /**
     * Gets the {@link ITrailer trailers} who are currently trailing the given {@link Entity}.
     *
     * @param entity the entity whose trailers we should get
     * @return the {@link ITrailer trailers} trailing the given entity
     * @deprecated Use {@link #getTrailersTrailingEntityByUUID(UUID)}
     */
    @Deprecated
    public List<ITrailer> getTrailersTrailingEntity(Entity entity);

    /**
     * Gets the {@link ITrailer trailers} who are currently trailing the given {@link UUID}.
     *
     * @param uuid the uuid of the player whose trailers we should get
     * @return the {@link ITrailer trailers} trailing the given player
     */
    public List<ITrailer> getTrailersTrailingEntityByUUID(UUID uuid);

    /**
     * Gets the list of all the current {@link ITrailer trailers}
     *
     * @return list of all the current trailers
     */
    public List<ITrailer> getAllTrailers();

    /**
     * Gets the list of the {@link PlayerTrailer player trailers}
     *
     * @return list of the current player trailers
     */
    public List<PlayerTrailer> getPlayerTrailers();

    /**
     * Gets the list of the {@link MobTrailer mob trailers}
     *
     * @return list of the current mob trailers
     */
    public List<MobTrailer> getMobTrailers();

    /**
     * Removes all of the current {@link ITrailer trailers} from being managed, only use after
     * removing them from everything else.
     *
     * @return the list of removed trailers
     */
    public List<ITrailer> removeAllTrailers();
}
