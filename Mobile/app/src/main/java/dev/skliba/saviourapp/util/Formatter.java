package dev.skliba.saviourapp.util;


import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.TextStyle;

import java.text.DecimalFormat;
import java.util.Locale;

public class Formatter {

    private static final String TIME_FORMAT = "%02d:%02d";

    private static final DecimalFormat SINGLE_DECIMAL_FORMAT = new DecimalFormat("0.0");

    private Formatter() {
        throw new UnsupportedOperationException("Cannot create instance of this class.");
    }

    public static String date(ZonedDateTime dateTime) {
        LocalDate localDate = dateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().toLocalDate();
        return date(localDate);
    }

    public static String date(LocalDate localDate) {
        // format date 'manually' because of issue in ThreeTen (https://github.com/ThreeTen/threetenbp/issues/50)
        return String.format(Locale.US, "%02d.%02d.%04d.",
                localDate.getDayOfMonth(),
                localDate.getMonthValue(),
                localDate.getYear());
    }

    /**
     * This method formats the date string in the following way: Friday, 24 March
     */
    public static String longDate(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return String.format(Locale.US, "%s, %d. %s",
                dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                date.getDayOfMonth(),
                date.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));
    }

    public static String time(ZonedDateTime dateTime) {
        LocalTime localTime = dateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();
        return time(localTime);
    }

    public static String time(LocalTime localTime) {
        // format time 'manually' because of issue in ThreeTen (https://github.com/ThreeTen/threetenbp/issues/50)
        return String.format(Locale.US, TIME_FORMAT, localTime.getHour(), localTime.getMinute());
    }

    /**
     * Helper method that uses ZonedDateTime object and splits it into the date and time individually and places the delimiter between them.
     * We need this for ZonedDateTime parsing and showing it to the ui.
     *
     * @param delimiter - whatever you need to be between date and time text, e.g. 28.03.2017 | 18:00 or 28.03.2017 18:00
     */
    public static String dateTime(ZonedDateTime dateTime, String delimiter) {
        dateTime = dateTime.withZoneSameInstant(ZoneId.systemDefault());
        return date(dateTime.toLocalDate()) + delimiter + time(dateTime.toLocalTime());
    }

    public static String iso8601dateTime(ZonedDateTime dateTime) {
        // produce string similar to 2016-12-07T11:32:55+01:00
        return String.format(Locale.US, "%04d-%02d-%02dT%02d:%02d:%02d%s",
                dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(),
                dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(),
                dateTime.getOffset().getId());
    }

    public static String singleDecimal(double f) {
        return SINGLE_DECIMAL_FORMAT.format(f);
    }
}
