package com.example.mymagicapp.helper;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.core.content.ContextCompat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utility {

    public static int widthPixelsDp(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int widthOfImage(Context context) {
        return (widthPixelsDp(context) / Constraints.SPAN_COUNT_ITEM_IMAGE) - 5;
    }

    public static LocalDate secondsToLocalDate(long seconds) {
        return Instant.ofEpochSecond(seconds).atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDate();
    }

    public static LocalDate secondsToLocalDate(String seconds) {
        long second = Long.parseLong(seconds);
        return secondsToLocalDate(second);
    }

    public static LocalDateTime secondsToLocalDateTime(long seconds){
        return Instant.ofEpochSecond(seconds).atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime();
    }

    public static LocalDateTime secondsToLocalDateTime(String seconds) {
        long second = Long.parseLong(seconds);
        return secondsToLocalDateTime(second);
    }

    public static String localDateTimeToString(LocalDateTime dateTime){
        return Long.toString(dateTime.toEpochSecond(ZoneOffset.UTC));
    }

    public static String localTimeToString(LocalTime time){
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static LocalTime randomTime(int timeStart, int timeEnd){
        final Random random = new Random();
        final int millisInDayOfTimeStart = timeStart * Constraints.MINUTE_COUNT_IN_HOUR * Constraints.MILLIS_COUNT_IN_MINUTE;
        final int millisInDayOfTimeEnd = timeEnd * Constraints.MINUTE_COUNT_IN_HOUR * Constraints.MILLIS_COUNT_IN_MINUTE;
        int range = millisInDayOfTimeEnd - millisInDayOfTimeStart;
        int rd = random.nextInt(range);
        return LocalTime.ofSecondOfDay(millisInDayOfTimeStart + rd);
    }
}