package com.graywolf336.trailingperspective.enums;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

/**
 * Represents the settings for the Trailing Perspective plugin.
 *
 * @author graywolf336
 * @since 1.0.0
 * @version 1.0.0
 */
public enum Settings {
    /** Whether we should be outputting debugging information. */
    DEBUG("system.debug"),
    /** The person who should be taken over and give the perspective to. */
    USERNAME("perspective.username"),
    /** The amount of time between changing between people. */
    CHANGE_INTERVAL("perspective.change-interval"),
    /** Determines whether or not the user is taken over upon logging in. */
    AUTOMATICALLY_ENABLED("perspective.automatically-enabled"),
    /** Determines whether or not to clear the trailer's chat they get. */
    CLEAR_TRAILERS_CHAT("perspective.clear-chat"),
    /** The setting where the trailers go when there is no one to trail on. */
    HOME_LOCATION("perspective.home-location"),
    /** The setting which controls at what light level the perspective gets night vision. */
    LIGHT_LEVEL_FOR_NIGHT_VISION("perspective.night-vision.light-level"),
    /** The setting which controls how long the night vision potion lasts in seconds. */
    NIGHT_VISION_POTION_LENGTH_SECONDS("perspective.night-vision.length-in-seconds"),
    /** The setting which contains a list of all the UUID's of players whom are blacklisted from being trailed. */
    PERSPECTIVE_BLACKLIST("perspective.blacklist"),
    
    //Hooks for plugins
    /** Checks whether the configuration has essentials hook enabled. */
    HOOKS_ESSENTIALS_ENABLED("hooks.essentials.enabled");

    private static Plugin pl;
    private String path;

    private Settings(String path) {
        this.path = path;
    }

    public boolean asBoolean() {
        return pl.getConfig().getBoolean(path);
    }

    public int asInt() {
        return pl.getConfig().getInt(path);
    }

    public byte asByte() {
        return Byte.valueOf(pl.getConfig().getString(path)).byteValue();
    }

    public String asString() {
        return pl.getConfig().getString(path);
    }

    public List<String> asStringList() {
        return pl.getConfig().getStringList(path);
    }

    public Location asLocation() {
        return (Location) pl.getConfig().get(path);
    }

    public void setAndSave(Object obj) {
        pl.getConfig().set(path, obj);
        pl.saveConfig();
    }

    public static void setPlugin(Plugin plugin) {
        pl = plugin;
    }
}
