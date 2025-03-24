package tc.arcadia.timedwings.utils;

import tc.arcadia.timedwings.TimedWings;
import org.bukkit.Bukkit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextUtils {
    private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}}");
    private static final StringBuilder BUILDER = new StringBuilder();
    private static final int VERSION = Integer.parseInt(Bukkit.getBukkitVersion()
            .split("-")[0]
            .split("\\.")[1]);
    private TextUtils() {}
    /**
     * Checks if the sequence given contains the next sequence specified without import if are Case Sensitive.
     *
     * @param target The string to check.
     * @param search The string to search into the string target specified.
     * @return True if the sequence was found, else return false.
     */
    public static boolean containsIgnoreCase(String target, String search) {
        if ((target == null) || search == null) return false;

        final int length = search.length();
        final int max = target.length() - length;

        for (int i = 0 ; i < max ; i++) {
            if (target.regionMatches(true, i, search, 0, length)) return true;
        }

        return false;
    }

    /**
     * Colorize the list content.
     *
     * @param textList A List of text.
     * @return The list colorized.
     */
    public static List<String> colorize(List<String> textList) {
        return textList.stream()
                .map(TextUtils::colorize)
                .collect(Collectors.toList());
    }

    /**
     * Colorize the string passed as parameter, if the server version number is lower than 16 (-1.16),
     * will colorize with the normal colors, overwise, will apply the HEX colors.
     * <p>
     *
     * @param text The text to colorize.
     * @return The text colorized.
     */
    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Formats a duration based on a given format string and total seconds. This function dynamically
     * calculates and inserts the appropriate years, months, days, hours, minutes, and seconds into the format string.
     * The calculations are made based on the presence of specific time unit placeholders in the format string.
     * For instance, if the format includes the placeholder '{y}', years will be computed and inserted.
     * If '{y}' is not present but '{mo}' is, then months are calculated next. If neither is present,
     * it skips to days, and so forth. The function ensures accurate pluralization of time units and
     * omits any units that are not present in the format string.
     * <p>
     * Examples:
     * - format: "{y} years {mo} months {d} days {h} hours {m} minutes {s} seconds", seconds: 9006100
     *   returns: "3 months 4 days 15 hours 35 minutes"
     * - format: "{mo} months {d} days {m} minutes {s} seconds", seconds: 2678400
     *   returns: "1 month 2 minutes"
     * <p>
     * @param format The string format defining the time units to include.
     * @param totalSeconds The total seconds to format.
     * @return A formatted string representing the duration in terms of the specified time units.
     */
    public static String formatDuration(String format, int totalSeconds, Player player) {
        TimedWings plugin = TimedWings.getInstance();
        FileConfiguration language = plugin.getLanguageManager().get(player);

        int years = 0, months = 0, days = 0, hours = 0, minutes = 0, seconds = 0;
        if (format.contains("{y}")) {
            years = totalSeconds / (365 * 24 * 60 * 60);
            totalSeconds %= (365 * 24 * 60 * 60);
        }
        if (format.contains("{mo}")) {
            months = totalSeconds / (30 * 24 * 60 * 60);
            totalSeconds %= (30 * 24 * 60 * 60);
        }
        if (format.contains("{d}")) {
            days = totalSeconds / (24 * 60 * 60);
            totalSeconds %= (24 * 60 * 60);
        }
        if (format.contains("{h}")) {
            hours = totalSeconds / (60 * 60);
            totalSeconds %= (60 * 60);
        }
        if (format.contains("{m}")) {
            minutes = totalSeconds / 60;
            totalSeconds %= 60;
        }
        if (format.contains("{s}")) {
            seconds = totalSeconds;
        }
        String formattedDuration = format;
        if (years > 0){
            String yearString = language.getString("general.time.year"+((years > 1) ? "s" : ""),"");
            formattedDuration = formattedDuration.replace("{y}", years +" "+ yearString);
        }
        if (months > 0){
            String monthString = language.getString("general.time.month"+((months > 1) ? "s" : ""),"");
            formattedDuration = formattedDuration.replace("{mo}", months +" "+ monthString);
        }
        if (days > 0) {
            String dayString = language.getString("general.time.day"+((days > 1) ? "s" : ""),"");
            formattedDuration = formattedDuration.replace("{d}", days +" "+ dayString);
        }
        if (hours > 0){
            String hourString = language.getString("general.time.hour"+((hours > 1) ? "s" : ""),"");
            formattedDuration = formattedDuration.replace("{h}", hours + " "+ hourString);
        }
        if (minutes > 0){
            String minuteString = language.getString("general.time.minute"+((minutes > 1) ? "s" : ""),"");
            formattedDuration = formattedDuration.replace("{m}", minutes + " "+ minuteString);
        }
        if (seconds > 0) {
            String secondString = language.getString("general.time.second"+((seconds > 1) ? "s" : ""),"");
            formattedDuration = formattedDuration.replace("{s}", seconds + " "+ secondString);
        }

        formattedDuration = formattedDuration.replace("{y}", "");
        formattedDuration = formattedDuration.replace("{mo}", "");
        formattedDuration = formattedDuration.replace("{d}", "");
        formattedDuration = formattedDuration.replace("{h}", "");
        formattedDuration = formattedDuration.replace("{m}", "");
        formattedDuration = formattedDuration.replace("{s}", "");

        return formattedDuration.trim();
    }






}
