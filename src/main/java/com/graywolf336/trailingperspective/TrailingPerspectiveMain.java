package com.graywolf336.trailingperspective;

import org.bukkit.plugin.java.JavaPlugin;

import com.graywolf336.trailingperspective.commands.ForceTrailerHomeCommand;
import com.graywolf336.trailingperspective.commands.SetTrailerHomeCommand;
import com.graywolf336.trailingperspective.commands.SwitchPerspectiveCommand;
import com.graywolf336.trailingperspective.commands.ToggleBeingMobTrailerCommand;
import com.graywolf336.trailingperspective.commands.ToggleBeingTrailerCommand;
import com.graywolf336.trailingperspective.enums.Settings;
import com.graywolf336.trailingperspective.interfaces.IHookManager;
import com.graywolf336.trailingperspective.interfaces.ITrailerManager;
import com.graywolf336.trailingperspective.listeners.TrailingPerspectivePlayerListener;
import com.graywolf336.trailingperspective.managers.HookManager;
import com.graywolf336.trailingperspective.managers.TrailerManager;
import com.graywolf336.trailingperspective.workers.HomeSendingWorker;
import com.graywolf336.trailingperspective.workers.SpectatorSetGameModeWorker;
import com.graywolf336.trailingperspective.workers.TrailerInformationWorker;
import com.graywolf336.trailingperspective.workers.TrailerNightVisionWorker;
import com.graywolf336.trailingperspective.workers.PlayerTrailersPerspectiveWorker;

public class TrailingPerspectiveMain extends JavaPlugin {
    private boolean debug = false;
    private ITrailerManager trailerManager;
    private IHookManager hookManager;

    public void onLoad() {
        this.saveDefaultConfig();

        TrailingPerspectiveAPI.setPlugin(this);
    }

    public void onEnable() {
        Settings.setPlugin(this);
        this.debug = Settings.DEBUG.asBoolean();

        this.trailerManager = new TrailerManager();
        this.hookManager = new HookManager(this);
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
        this.hookManager = null;
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
     * Gets the current instance of the {@link IHookManager}.
     *
     * @return the hook manager instance
     */
    public IHookManager getHookManager() {
        return this.hookManager;
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
                    this.getServer().getConsoleSender().sendMessage("[TrailingPerspective] [Debug]: " + s);
                } else {
                    this.getLogger().info("[Debug]: " + s);
                }
            }
        }
    }

    private void setupCommands() {
        ToggleBeingTrailerCommand toggleCmd = new ToggleBeingTrailerCommand(this);
        this.getCommand("togglebeingtrailer").setExecutor(toggleCmd);
        this.getCommand("togglebeingtrailer").setTabCompleter(toggleCmd);

        ToggleBeingMobTrailerCommand toggleMobCmd = new ToggleBeingMobTrailerCommand(this);
        this.getCommand("togglebeingmobtrailer").setExecutor(toggleMobCmd);
        this.getCommand("togglebeingmobtrailer").setTabCompleter(toggleMobCmd);

        SetTrailerHomeCommand homeCmd = new SetTrailerHomeCommand();
        this.getCommand("settrailerhome").setExecutor(homeCmd);
        this.getCommand("settrailerhome").setTabCompleter(homeCmd);

        SwitchPerspectiveCommand switchCmd = new SwitchPerspectiveCommand(this);
        this.getCommand("switchperspective").setExecutor(switchCmd);
        this.getCommand("switchperspective").setTabCompleter(switchCmd);

        ForceTrailerHomeCommand forceHomeCmd = new ForceTrailerHomeCommand(this);
        this.getCommand("forcetrailerhome").setExecutor(forceHomeCmd);
        this.getCommand("forcetrailerhome").setTabCompleter(forceHomeCmd);
    }

    private void setupWorkers() {
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new SpectatorSetGameModeWorker(this), 20, 5);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new PlayerTrailersPerspectiveWorker(this), 20, 10);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TrailerInformationWorker(this), 20, 10);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new HomeSendingWorker(this), 20, 20);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TrailerNightVisionWorker(this), 10, 15);
    }
}
