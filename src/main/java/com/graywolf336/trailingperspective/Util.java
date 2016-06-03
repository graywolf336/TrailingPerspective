package com.graywolf336.trailingperspective;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;

public class Util {
    private final static Pattern DURATION_PATTERN = Pattern.compile("^(\\d+)\\s*(m(?:inute)?s?|h(?:ours?)?|d(?:ays?)?|s(?:econd)?s?)?$", Pattern.CASE_INSENSITIVE);
    private final static int MILLISECONDS_PER_TICK = 50;

    /**
     * Converts a string like '20minutes' into the appropriate amount of ticks.
     *
     * @param time in a string to convert.
     * @return The time in ticks that is converted or default of 20 ticks.
     */
    public static Long getTime(String time) {
        if (time.equalsIgnoreCase("-1"))
            return -1L;

        Long t = 20L;
        Matcher match = DURATION_PATTERN.matcher(time);

        if (match.matches()) {
            String units = match.group(2);
            if ("seconds".equals(units) || "second".equals(units) || "s".equals(units)) {
                t = TimeUnit.MILLISECONDS.convert(Long.valueOf(match.group(1)), TimeUnit.SECONDS);
            } else if ("minutes".equals(units) || "minute".equals(units) || "mins".equals(units) || "min".equals(units) || "m".equals(units)) {
                t = TimeUnit.MILLISECONDS.convert(Long.valueOf(match.group(1)), TimeUnit.MINUTES);
            } else if ("hours".equals(units) || "hour".equals(units) || "h".equals(units)) {
                t = TimeUnit.MILLISECONDS.convert(Long.valueOf(match.group(1)), TimeUnit.HOURS);
            } else if ("days".equals(units) || "day".equals(units) || "d".equals(units)) {
                t = TimeUnit.MILLISECONDS.convert(Long.valueOf(match.group(1)), TimeUnit.DAYS);
            } else {
                try {
                    t = TimeUnit.MILLISECONDS.convert(Long.parseLong(time), TimeUnit.MINUTES);
                } catch (NumberFormatException e) {
                    Bukkit.getLogger().warning("Failed to parse the time passed in \"" + time + "\"");
                }
            }
        }

        // 1 tick is 50 milliseconds
        return t / MILLISECONDS_PER_TICK;
    }
}
