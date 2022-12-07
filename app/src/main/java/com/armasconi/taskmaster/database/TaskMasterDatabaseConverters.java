package com.armasconi.taskmaster.database;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

// TODO Step: 4-6 Create database converters for the date!
public class TaskMasterDatabaseConverters {
    @TypeConverter
    public static Date fromTimeStamp(Long dateValue) {
        return dateValue == null ? null : new Date(dateValue);
    }

    @TypeConverter
    public static Long dateToTimeStamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
