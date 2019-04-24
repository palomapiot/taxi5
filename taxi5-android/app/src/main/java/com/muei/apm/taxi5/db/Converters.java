package com.muei.apm.taxi5.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;

public class Converters {

    @TypeConverter
    public static Calendar fromTimestamp(Long value) {
        if (value == null) {
            return null;
        } else {
            Calendar res = Calendar.getInstance();
            res.setTimeInMillis(value);
            return res;
        }
    }

    @TypeConverter
    public static Long calendarToTimestamp(Calendar date) {
        return date == null ? null : date.getTimeInMillis();
    }

}
