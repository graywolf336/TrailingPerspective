package com.graywolf336.trailingperspective;

import com.graywolf336.trailingperspective.interfaces.ITrailerManager;

/**
 * Contains static api for interacting with the plugin.
 *
 * @author graywolf336
 * @since 1.0.0
 * @version 1.0.0
 */
public class TrailingPerspectiveAPI {
    private static TrailingPerspectiveMain plugin;

    protected static void setPlugin(TrailingPerspectiveMain pl) {
        plugin = pl;
    }

    /**
     * Gets the a valid {@link ITrailerManager} instance.
     *
     * @return {@link ITrailerManager} instance
     */
    public static ITrailerManager getTrailerManager() {
        return plugin.getTrailerManager();
    }
}
