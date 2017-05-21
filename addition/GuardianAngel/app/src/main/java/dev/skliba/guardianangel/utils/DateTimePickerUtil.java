package dev.skliba.guardianangel.utils;


import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

public class DateTimePickerUtil {

    private DateTimePickerUtil() {
    }

    public static void chooseDate(@NonNull FragmentManager fm, @NonNull OnDateSelectedListener listener) {
        chooseDate(fm, null, listener);
    }

    public static void chooseDate(@NonNull FragmentManager fm, @Nullable LocalDate initialDate, @NonNull OnDateSelectedListener listener) {
        LocalDate date = initialDate != null ? initialDate : LocalDate.now();
        DatePickerDialog datePickerDialog = DatePickerDialog
                .newInstance((view, year, monthOfYear, dayOfMonth) -> {
                            LocalDate selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                            listener.onDateSelected(selectedDate);
                        },
                        date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        datePickerDialog.show(fm, "date picker dialog");
    }

    public static void chooseTime(@NonNull FragmentManager fm, @NonNull OnTimeSelectedListener listener) {
        chooseTime(fm, null, listener);
    }

    public static void chooseTime(@NonNull FragmentManager fm, @Nullable LocalTime initialTime, @NonNull OnTimeSelectedListener listener) {
        LocalTime time = initialTime != null ? initialTime : LocalTime.now();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance((view, hourOfDay, minute, second) -> {
                    LocalTime selectedTime = LocalTime.of(hourOfDay, minute, second);
                    listener.onTimeSelected(selectedTime);
                },
                time.getHour(), time.getMinute(), true);
        timePickerDialog.show(fm, "time picker dialog");
    }

    public static void chooseDateTime(@NonNull FragmentManager fm, @NonNull OnDateTimeSelectedListener listener) {
        chooseDateTime(fm, null, listener);
    }

    public static void chooseDateTime(@NonNull FragmentManager fm, @Nullable ZonedDateTime initialDateTime,
            @NonNull OnDateTimeSelectedListener listener) {
        ZonedDateTime dateTime = initialDateTime != null ? initialDateTime.withZoneSameInstant(ZoneId.systemDefault())
                : ZonedDateTime.now();
        chooseDate(fm, dateTime.toLocalDate(),
                date -> chooseTime(fm, dateTime.toLocalTime(),
                        time -> {
                            ZonedDateTime selectedDateTime = date.atTime(time).atZone(ZoneId.systemDefault());
                            listener.onDateTimeSelected(selectedDateTime);
                        }));
    }

    public interface OnDateSelectedListener {

        void onDateSelected(LocalDate date);
    }

    public interface OnTimeSelectedListener {

        void onTimeSelected(LocalTime time);
    }

    public interface OnDateTimeSelectedListener {

        void onDateTimeSelected(ZonedDateTime dateTime);
    }
}
