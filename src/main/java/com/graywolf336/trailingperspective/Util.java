package com.graywolf336.trailingperspective;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.earth2me.essentials.Essentials;
import com.graywolf336.trailingperspective.interfaces.ITrailer;

public class Util {
    public final static int TICKS_PER_SECOND = 20;
    private final static Pattern DURATION_PATTERN = Pattern.compile("^(\\d+)\\s*(m(?:inute)?s?|h(?:ours?)?|d(?:ays?)?|s(?:econd)?s?)?$", Pattern.CASE_INSENSITIVE);
    private final static Random random = new Random(System.currentTimeMillis());

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
     * Convert a millisecond duration to a string format
     *
     * @param millis A duration to convert to a string form
     * @return A string of the form "X days Y hours Z minutes A seconds".
     */
    public static String getDurationBreakdown(long millis) {
        long ms = millis;
        if (ms < 0)
            return "ASAP";

        long days = TimeUnit.MILLISECONDS.toDays(ms);
        ms -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(ms);
        ms -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(ms);
        ms -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(ms);

        return getDurationString(days, hours, minutes, seconds);
    }

    private static String getDurationString(long days, long hours, long minutes, long seconds){
        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days);
            sb.append(" day" + (days == 1 ? "" : "s") + " ");
        }

        if (days > 0 || hours > 0) {
            sb.append(hours);
            sb.append(" hour" + (hours == 1 ? "" : "s") + " ");
        }

        if (days > 0 || hours > 0 || minutes > 0) {
            sb.append(minutes);
            sb.append(" minute" + (minutes == 1 ? "" : "s") + " ");
        }

        sb.append(seconds);
        sb.append(" second" + (seconds == 1 ? "" : "s"));
        
        return sb.toString();
    }
    
    /**
     * Gets a random {@link Player} whose name is not a trailer or in the given list of usernames.
     *
     * @param trailers the list of {@link ITrailer trailers}
     * @param names the names to ignore.
     * @return a random {@link Player} <strong>OR</strong> null
     */
    public static Player getRandomAlivePlayerNotInList(List<ITrailer> trailers, String... names) {
        Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        List<String> namesToCheck = new ArrayList<String>();
        namesToCheck.addAll(trailers.stream().map(t -> t.getUsername()).collect(Collectors.toList()));

        for (String s : names) {
            namesToCheck.add(s);
        }

        List<Player> players = Bukkit.getOnlinePlayers().stream().filter(p -> !namesToCheck.contains(p.getName()) && !p.isDead() && !ess.getUser(p.getName()).isAfk()).collect(Collectors.toList());

        switch (players.size()) {
            case 0:
                return null;
            case 1:
                return players.get(0);
            default:
                return players.get(random.nextInt(players.size() - 1));
        }
    }
}
