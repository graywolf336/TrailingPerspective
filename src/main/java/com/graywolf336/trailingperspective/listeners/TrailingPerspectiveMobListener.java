package com.graywolf336.trailingperspective.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.graywolf336.trailingperspective.TrailingPerspectiveMain;

public class TrailingPerspectiveMobListener implements Listener {
    private TrailingPerspectiveMain pl;

    public TrailingPerspectiveMobListener(TrailingPerspectiveMain plugin) {
        this.pl = plugin;
    }
    
    @EventHandler
    public void entityWentByeBye(EntityDeathEvent event) { 
        this.setNoLongerTrailingEntity(event.getEntity());
    }
    
    private void setNoLongerTrailingEntity(Entity e) {
        this.pl.getTrailerManager().getTrailersTrailingEntityByUUID(e.getUniqueId()).forEach(t -> t.setNoLongerTrailingAnything());
    }
}
