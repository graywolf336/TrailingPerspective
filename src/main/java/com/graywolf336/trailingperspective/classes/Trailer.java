package com.graywolf336.trailingperspective.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.interfaces.ITrailer;

public class Trailer implements ITrailer {
    private List<String> previousTrailed;
    private String cachedUsername;
    private UUID uuid;
    private UUID trailingPlayerUuid;
    private long startTime;
    private long currentTrailingStartTime;

    public Trailer(Player player) {
        this.previousTrailed = new ArrayList<String>();
        this.uuid = player.getUniqueId();
        this.cachedUsername = player.getName();
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

    public Long getTotalTimeTrailing() {
        return this.startTime == 0 ? this.startTime : System.currentTimeMillis() - this.startTime;
    }

    public boolean isCurrentlyTrailingSomeone() {
        return this.trailingPlayerUuid != null;
    }

    public Long getCurrentPerspectiveTrailingTime() {
        return this.currentTrailingStartTime == 0 ? this.currentTrailingStartTime : System.currentTimeMillis() - this.currentTrailingStartTime;
    }

    public void setPlayerCurrentlyTrailing(Player player) {
        this.trailingPlayerUuid = player.getUniqueId();
        this.currentTrailingStartTime = System.currentTimeMillis();
        this.previousTrailed.add(player.getName());
    }

    public Player getPlayerCurrentlyTrailing() {
        return this.trailingPlayerUuid == null ? null : Bukkit.getPlayer(this.trailingPlayerUuid);
    }

    public UUID getUUIDOfPlayerCurrentlyTrailing() {
        return this.trailingPlayerUuid;
    }

    public void setNoLongerTrailingAnyone() {
        this.trailingPlayerUuid = null;
        this.currentTrailingStartTime = 0L;
    }

    public List<String> getPlayersLastTrailed() {
        return this.previousTrailed;
    }
}