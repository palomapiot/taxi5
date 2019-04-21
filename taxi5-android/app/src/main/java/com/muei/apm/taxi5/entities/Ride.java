package com.muei.apm.taxi5.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "user_id",
        onDelete = CASCADE))
public class Ride {

    @PrimaryKey
    @NonNull
    public long rid;

    public String origin;

    public String destination;

    public Calendar date;

    @Ignore
    public String dateString;

    @ColumnInfo(name = "user_id")
    public long userId;

    public Ride(String origin, String destination, Calendar date, long userId) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.userId = userId;
    }

    public Ride(Ride r) {
        this.origin = r.origin;
        this.destination = r.destination;
        this.date = r.date;
        this.userId = r.userId;
        SimpleDateFormat f = new SimpleDateFormat("EEEE, dd/MM/yyyy");
        this.dateString = f.format(this.date.getTime());
    }

}
