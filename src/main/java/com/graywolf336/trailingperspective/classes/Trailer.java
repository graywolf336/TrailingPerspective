package com.graywolf336.trailingperspective.classes;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.interfaces.ITrailer;

public abstract class Trailer implements ITrailer {
    private UUID playerUuid;
    private String playerUsername;
    private boolean wasSentHome;
    private boolean isForcedHome;

    public Trailer(Player player) {
        this.playerUuid = player.getUniqueId();
        this.playerUsername = player.getName();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.playerUuid);
    }

    public UUID getUUID() {
        return this.playerUuid;
    }

    public String getUsername() {
        return this.playerUsername;
    }

    public boolean isOnline() {
        return Bukkit.getPlayer(this.playerUuid) != null;
    }

    public boolean wasSentHome() {
        return this.wasSentHome;
    }

    public void setWhetherTheyHaveBeenSentHome(boolean sent) {
        this.wasSentHome = sent;
    }

    public boolean isForcedHome() {
        return this.isForcedHome;
    }

    public void forcePlayerHome() {
        this.isForcedHome = true;
    }

    public void unForcePlayerHome() {
        this.isForcedHome = false;
    }
}
