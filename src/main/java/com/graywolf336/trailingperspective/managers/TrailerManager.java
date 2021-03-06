package com.graywolf336.trailingperspective.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.TrailingPerspectiveAPI;
import com.graywolf336.trailingperspective.classes.MobTrailer;
import com.graywolf336.trailingperspective.classes.PlayerTrailer;
import com.graywolf336.trailingperspective.enums.Settings;
import com.graywolf336.trailingperspective.events.NewMobTrailerEvent;
import com.graywolf336.trailingperspective.events.NewPlayerTrailerEvent;
import com.graywolf336.trailingperspective.interfaces.ITrailer;
import com.graywolf336.trailingperspective.interfaces.ITrailerManager;

public class TrailerManager implements ITrailerManager {
    private List<ITrailer> trailers;
    private List<String> blacklist;

    public TrailerManager(List<String> blacklist) {
        this.trailers = new ArrayList<ITrailer>();
        this.blacklist = blacklist;
    }

    public void addTrailer(ITrailer trailer) {
        this.trailers.add(trailer);

        if (trailer instanceof PlayerTrailer) {
            TrailingPerspectiveAPI.debug(false, trailer.getPlayer().getName() + " is now a PlayerTrailer.");
            Bukkit.getPluginManager().callEvent(new NewPlayerTrailerEvent(trailer.getPlayer(), (PlayerTrailer) trailer));
        } else if (trailer instanceof MobTrailer) {
            TrailingPerspectiveAPI.debug(false, trailer.getPlayer().getName() + " is now a MobTrailer.");
            Bukkit.getPluginManager().callEvent(new NewMobTrailerEvent(trailer.getPlayer(), (MobTrailer) trailer));
        }
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

    public boolean isPlayerTrailer(Player player) {
        return this.isPlayerTrailer(player.getUniqueId());
    }

    public boolean isPlayerTrailer(UUID uuid) {
        return this.trailers.stream().filter(t -> t instanceof PlayerTrailer).filter(t -> t.getUUID().equals(uuid)).findFirst().isPresent();
    }

    public boolean isMobTrailer(Player player) {
        return this.isMobTrailer(player.getUniqueId());
    }

    public boolean isMobTrailer(UUID uuid) {
        return this.trailers.stream().filter(t -> t instanceof MobTrailer).filter(t -> t.getUUID().equals(uuid)).findFirst().isPresent();
    }

    public List<ITrailer> getAllTrailers() {
        return this.trailers;
    }

    public List<PlayerTrailer> getPlayerTrailers() {
        return this.getPlayerTrailersStream().collect(Collectors.toList());
    }

    public Stream<PlayerTrailer> getPlayerTrailersStream() {
        return this.trailers.stream().filter(t -> t instanceof PlayerTrailer).map(PlayerTrailer.class::cast);
    }

    public List<MobTrailer> getMobTrailers() {
        return this.getMobTrailersStream().collect(Collectors.toList());
    }

    public Stream<MobTrailer> getMobTrailersStream() {
        return this.trailers.stream().filter(t -> t instanceof MobTrailer).map(MobTrailer.class::cast);
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

    public List<UUID> getBlacklist() {
        return this.blacklist.stream().map(UUID::fromString).collect(Collectors.toList());
    }

    public Stream<String> getBlacklistStream() {
        return this.blacklist.stream();
    }

    public void addToBlacklist(UUID id) {
        this.blacklist.add(id.toString());
        Settings.PERSPECTIVE_BLACKLIST.setAndSave(this.blacklist);
    }

    public boolean isBlacklisted(UUID id) {
        return this.blacklist.contains(id.toString());
    }

    public void removeFromBlacklist(UUID id) {
        this.blacklist.remove(id);
        Settings.PERSPECTIVE_BLACKLIST.setAndSave(this.blacklist);
    }
}
