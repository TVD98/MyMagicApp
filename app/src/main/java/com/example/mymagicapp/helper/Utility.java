package com.example.mymagicapp.helper;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.core.content.ContextCompat;

import java.net.NetworkInterface;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utility {
    public final static ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");

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

    public static String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null)
            return "";
        return Long.toString(dateTime.toEpochSecond(ZoneOffset.UTC));
    }

    public static String localTimeToString(LocalTime time){
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static LocalDateTime secondsToLocalDateTime(long seconds) {
        return Instant.ofEpochSecond(seconds).atZone(zoneId).toLocalDateTime();
    }

    public static LocalDateTime secondsToLocalDateTime(String seconds) {
        if (seconds.isEmpty())
            return null;
        long second = Long.parseLong(seconds);
        return secondsToLocalDateTime(second);
    }

    public static String secondsToLocalDateTimeFormat(String seconds) {
        if (seconds.isEmpty())
            return "";
        else {
            long second = Long.parseLong(seconds);
            return secondsToLocalDateTimeFormat(second);
        }
    }

    public static String secondsToLocalDateTimeFormat(long seconds) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return secondsToLocalDateTime(seconds).format(format);
    }

    public static LocalTime randomTime(int timeStart, int timeEnd){
        final Random random = new Random();
        final int millisInDayOfTimeStart = timeStart * Constraints.MINUTE_COUNT_IN_HOUR * Constraints.MILLIS_COUNT_IN_MINUTE;
        final int millisInDayOfTimeEnd = timeEnd * Constraints.MINUTE_COUNT_IN_HOUR * Constraints.MILLIS_COUNT_IN_MINUTE;
        int range = millisInDayOfTimeEnd - millisInDayOfTimeStart;
        int rd = random.nextInt(range);
        return LocalTime.ofSecondOfDay(millisInDayOfTimeStart + rd);
    }

    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    // res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            //handle exception
        }
        return "";
    }
}