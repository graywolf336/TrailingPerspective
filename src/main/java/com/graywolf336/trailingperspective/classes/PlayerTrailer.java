package com.graywolf336.trailingperspective.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PlayerTrailer extends Trailer {
    private List<String> previousTrailed;
    private UUID trailingPlayerUuid;
    private long startTime;
    private long currentTrailingStartTime;

    public PlayerTrailer(Player player) {
        super(player);
        this.previousTrailed = new ArrayList<String>();
    }

    public long getTotalTimeTrailing() {
        return this.startTime == 0 ? this.startTime : System.currentTimeMillis() - this.startTime;
    }

    public boolean isCurrentlyTrailingSomething() {
        return this.trailingPlayerUuid != null;
    }

    public long getCurrentPerspectiveTrailingTime() {
        return this.currentTrailingStartTime == 0 ? this.currentTrailingStartTime : System.currentTimeMillis() - this.currentTrailingStartTime;
    }

    public void setEntityCurrentlyTrailing(Entity entity) {
        this.setWhetherTheyHaveBeenSentHome(false);
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
}
