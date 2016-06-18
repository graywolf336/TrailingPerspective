package com.graywolf336.trailingperspective.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.classes.MobTrailer;
import com.graywolf336.trailingperspective.classes.PlayerTrailer;
import com.graywolf336.trailingperspective.interfaces.ITrailer;
import com.graywolf336.trailingperspective.interfaces.ITrailerManager;

public class TrailerManager implements ITrailerManager {
    private List<ITrailer> trailers;

    public TrailerManager() {
        this.trailers = new ArrayList<ITrailer>();
    }

    public void addTrailer(ITrailer trailer) {
        this.trailers.add(trailer);
    }

    public boolean removeTrailer(ITrailer trailer) {
        return this.trailers.remove(trailer);
    }

    public boolean removeTrailer(UUID uuid) {
        Optional<ITrailer> trailer = this.trailers.stream().filter(t -> t.getUUID().equals(uuid)).findFirst();

        return trailer.isPresent() ? this.trailers.remove(trailer.get()) : false;
    }

    public ITrailer getTrailer(Player player) {
        return this.getTrailer(player.getUniqueId());
    }

    public ITrailer getTrailer(UUID uuid) {
        return this.trailers.stream().filter(t -> t.getUUID().equals(uuid)).findFirst().orElse(null);
    }

    public boolean isTrailer(Player player) {
        return this.isTrailer(player.getUniqueId());
    }

    public boolean isTrailer(UUID uuid) {
        return this.trailers.stream().filter(t -> t.getUUID().equals(uuid)).findFirst().isPresent();
    }

    public List<ITrailer> getAllTrailers() {
        return this.trailers;
    }

    public List<PlayerTrailer> getPlayerTrailers() {
        return this.trailers.stream().filter(t -> t instanceof PlayerTrailer).map(PlayerTrailer.class::cast).collect(Collectors.toList());
    }

    public List<MobTrailer> getMobTrailers() {
        return this.trailers.stream().filter(t -> t instanceof PlayerTrailer).map(MobTrailer.class::cast).collect(Collectors.toList());
    }

    public boolean isBeingTrailed(Entity entity) {
        return this.isBeingTrailed(entity.getUniqueId());
    }

    public boolean isBeingTrailed(UUID uuid) {
        return this.trailers.stream().filter(t -> t.isCurrentlyTrailingSomething() && t.getUUIDOfEntityCurrentlyTrailing().equals(uuid)).findAny().isPresent();
    }

    public List<ITrailer> getTrailersTrailingEntity(Entity entity) {
        return this.getTrailersTrailingEntityByUUID(entity.getUniqueId());
    }

    public List<ITrailer> getTrailersTrailingEntityByUUID(UUID uuid) {
        return this.trailers.stream().filter(t -> t.getUUIDOfEntityCurrentlyTrailing().equals(uuid)).collect(Collectors.toList());
    }

    public List<ITrailer> removeAllTrailers() {
        List<ITrailer> preTrailers = new ArrayList<ITrailer>(this.trailers);

        for (ITrailer trailer : this.trailers) {
            trailer.setNoLongerTrailingAnything();

            if (trailer.isOnline() && trailer.getPlayer().getGameMode() == GameMode.SPECTATOR) {
                trailer.getPlayer().setSpectatorTarget(null);
            }
        }

        // TODO: Maybe look into locking this down? Idk
        this.trailers.clear();

        return preTrailers;
    }
}
