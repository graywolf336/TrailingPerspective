package com.graywolf336.trailingperspective;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Util {
    private final static Pattern DURATION_PATTERN = Pattern.compile("^(\\d+)\\s*(m(?:inute)?s?|h(?:ours?)?|d(?:ays?)?|s(?:econd)?s?)?$", Pattern.CASE_INSENSITIVE);

    /**
     * Converts a string like '20minutes' into the appropriate amount of milliseconds.
     *
     * @param time in a string to convert.
     * @param fallback the time to fallback to if we failed to parse
     * @return The time in milliseconds that is converted.
     */
    public static Long getTime(String time, Long fallback) {
        if (time.equalsIgnoreCase("-1"))
            return -1L;

        Long t = 1000L;
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
                    t = fallback;
                }
            }
        } else {
            t = fallback;
        }

        return t;
    }

    /**
     * Gets a random {@link Player} whose name is not equal to the given name
     *
     * @param names the names to ignore.
     * @return a random {@link Player} <strong>OR</strong> null
     */
    public static Player getRandomPlayerNotInList(String... names) {
        List<String> namesToCheck = Arrays.asList(names);

        return Bukkit.getOnlinePlayers().stream().filter(p -> !namesToCheck.contains(p.getName())).findAny().orElse(null);
    }
}
