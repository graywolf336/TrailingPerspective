package com.graywolf336.trailingperspective.classes;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.graywolf336.trailingperspective.interfaces.ITrailer;

public class MobTrailer implements ITrailer {
    private UUID uuid;
    private String cachedUsername;
    private Entity entity;

    public MobTrailer(Player player) {
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

    @Override
    public boolean wasSentHome() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setWhetherTheyHaveBeenSentHome(boolean sent) {
        // TODO Auto-generated method stub

    }

    @Override
    public Long getTotalTimeTrailing() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCurrentlyTrailingSomething() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Long getCurrentPerspectiveTrailingTime() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setEntityCurrentlyTrailing(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntityCurrentlyTrailing() {
        return this.entity;
    }

    public UUID getUUIDOfEntityCurrentlyTrailing() {
        return this.entity.getUniqueId();
    }

    @Override
    public void setNoLongerTrailingAnything() {
        // TODO Auto-generated method stub

    }

    @Override
    public void flagReadyToGoNext() {
        // TODO Auto-generated method stub

    }

    public List<String> getEntitiesLastTrailed() {
        return Collections.emptyList();
    }

    @Override
    public boolean isForcedHome() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void forcePlayerHome() {
        // TODO Auto-generated method stub

    }

    @Override
    public void unForcePlayerHome() {
        // TODO Auto-generated method stub

    }

}
