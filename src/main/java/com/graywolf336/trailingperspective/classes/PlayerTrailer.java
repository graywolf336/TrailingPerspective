package com.graywolf336.trailingperspective.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.interfaces.ITrailer;

public class PlayerTrailer implements ITrailer {
    private List<String> previousTrailed;
    private String cachedUsername;
    private UUID uuid;
    private UUID trailingPlayerUuid;
    private long startTime;
    private long currentTrailingStartTime;
    private boolean wasSentHome;
    private boolean isForcedHome;

    public PlayerTrailer(Player player) {
        this.previousTrailed = new ArrayList<String>();
        this.uuid = player.getUniqueId();
        this.cachedUsername = player.getName();
        this.isForcedHome = false;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String getUsername() {
        return this.cachedUsername;
    }

    public boolean isOnline() {
        return Bukkit.getPlayer(uuid) != null;
    }

    public boolean wasSentHome() {
        return this.wasSentHome;
    }

    public void setWhetherTheyHaveBeenSentHome(boolean sent) {
        this.wasSentHome = sent;
    }

    public Long getTotalTimeTrailing() {
        return this.startTime == 0 ? this.startTime : System.currentTimeMillis() - this.startTime;
    }

    public boolean isCurrentlyTrailingSomething() {
        return this.trailingPlayerUuid != null;
    }

    public Long getCurrentPerspectiveTrailingTime() {
        return this.currentTrailingStartTime == 0 ? this.currentTrailingStartTime : System.currentTimeMillis() - this.currentTrailingStartTime;
    }

    public void setEntityCurrentlyTrailing(Entity entity) {
        this.wasSentHome = false;
        this.trailingPlayerUuid = entity.getUniqueId();
        this.currentTrailingStartTime = System.currentTimeMillis();
        this.previousTrailed.add(entity.getName());
    }

    public Player getEntityCurrentlyTrailing() {
        return this.trailingPlayerUuid == null ? null : Bukkit.getPlayer(this.trailingPlayerUuid);
    }

    public UUID getUUIDOfEntityCurrentlyTrailing() {
        return this.trailingPlayerUuid;
    }

    public void setNoLongerTrailingAnything() {
        this.trailingPlayerUuid = null;
        this.currentTrailingStartTime = 0L;
    }

    public void flagReadyToGoNext() {
        this.currentTrailingStartTime = 0L;
    }

    public List<String> getEntitiesLastTrailed() {
        return this.previousTrailed;
    }

    public void forcePlayerHome() {
        this.isForcedHome = true;
    }

    public void unForcePlayerHome() {
        this.isForcedHome = false;
    }

    public boolean isForcedHome() {
        return this.isForcedHome;
    }
}
