package com.graywolf336.trailingperspective.classes;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.Util;

public class MobTrailer extends Trailer {
    private Entity entity;
    private long currentTrailingStartTime;

    public MobTrailer(Player player) {
        super(player);
    }

    public MobTrailer(Player player, Entity entity) {
        super(player);
        this.entity = entity;
    }

    public long getTotalTimeTrailing() {
        return this.getCurrentPerspectiveTrailingTime();
    }

    public boolean isCurrentlyTrailingSomething() {
        return this.entity != null;
    }

    public long getCurrentPerspectiveTrailingTime() {
        return this.currentTrailingStartTime == 0 ? this.currentTrailingStartTime : System.currentTimeMillis() - this.currentTrailingStartTime;
    }

    public void setEntityCurrentlyTrailing(Entity entity) {
        this.setWhetherTheyHaveBeenSentHome(false);
        this.currentTrailingStartTime = System.currentTimeMillis();
        this.entity = entity;
    }

    public Entity getEntityCurrentlyTrailing() {
        return this.entity;
    }

    public UUID getUUIDOfEntityCurrentlyTrailing() {
        return this.entity == null ? null : this.entity.getUniqueId();
    }

    public void setNoLongerTrailingAnything() {
        this.entity = null;
        this.currentTrailingStartTime = 0L;
    }

    public void flagReadyToGoNext() {
        ;// This type of Trailer doesn't respect this flag
    }

    public List<String> getEntitiesLastTrailed() {
        return Arrays.asList(new String[] { entity == null ? "" : Util.getEntityTypeNiceString(entity.getType()) });
    }
}
