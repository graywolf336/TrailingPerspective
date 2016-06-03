package com.graywolf336.trailingperspective.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

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

    public List<ITrailer> getTrailers() {
        return this.trailers;
    }

    public List<ITrailer> removeAllTrailers() {
        List<ITrailer> preTrailers = new ArrayList<ITrailer>(this.trailers);
        
        //TODO: Maybe look into locking this down? Idk
        this.trailers.clear();
        
        return preTrailers;
    }
}
