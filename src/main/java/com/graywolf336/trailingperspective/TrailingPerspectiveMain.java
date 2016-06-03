package com.graywolf336.trailingperspective;

import org.bukkit.plugin.java.JavaPlugin;

import com.graywolf336.trailingperspective.commands.ToggleBeingTrailer;
import com.graywolf336.trailingperspective.enums.Settings;
import com.graywolf336.trailingperspective.interfaces.ITrailerManager;
import com.graywolf336.trailingperspective.listeners.TrailingPerspectivePlayerListener;
import com.graywolf336.trailingperspective.managers.TrailerManager;
import com.graywolf336.trailingperspective.workers.SpectatorSetWorker;
import com.graywolf336.trailingperspective.workers.TrailersPerspectiveWorker;

public class TrailingPerspectiveMain extends JavaPlugin {
    private boolean debug = false;
    private ITrailerManager trailerManager;

    public void onLoad() {
        this.saveDefaultConfig();
        Settings.setPlugin(this);
        this.debug = Settings.DEBUG.asBoolean();
    }

    public void onEnable() {
        this.trailerManager = new TrailerManager();
        this.getServer().getPluginManager().registerEvents(new TrailingPerspectivePlayerListener(this), this);
        this.setupCommands();
        this.setupWorkers();
    }

    public void onDisable() {
        if (this.trailerManager != null) {
            this.trailerManager.removeAllTrailers();
        }

        this.getServer().getScheduler().cancelTasks(this);
        this.trailerManager = null;
    }

    /**
     * Gets the current instance of the {@link ITrailerManager}.
     *
     * @return the trailer manager instance
     */
    public ITrailerManager getTrailerManager() {
        return this.trailerManager;
    }

    /**
     * Prints messages if the plugin is in a debugging state.
     *
     * @param colored whether the messages are colored.
     * @param msgs the messages to print.
     */
    public void debug(boolean colored, String... msgs) {
        if (this.debug) {
            for (String s : msgs) {
                if (colored) {
                    getServer().getConsoleSender().sendMessage("[TrailingPerspective] [Debug]: " + s);
                } else {
                    getLogger().info("[Debug]: " + s);
                }
            }
        }
    }

    private void setupCommands() {
        ToggleBeingTrailer toggleCmd = new ToggleBeingTrailer(this);
        this.getCommand("togglebeingtrailer").setExecutor(toggleCmd);
        this.getCommand("togglebeingtrailer").setTabCompleter(toggleCmd);
    }

    private void setupWorkers() {
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new SpectatorSetWorker(this), 20, 5);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TrailersPerspectiveWorker(this), 20, 10);
    }
}
