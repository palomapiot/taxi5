package com.muei.apm.taxifive.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.muei.apm.taxifive.entities.Ride;

import java.util.List;

@Dao
public interface RideDao {


    @Query("SELECT * FROM ride")
    List<Ride> getAll();

    @Query("SELECT * FROM ride WHERE id IN (:rideIds)")
    List<Ride> loadAllByIds(long[] rideIds);


    @Query("SELECT * FROM ride WHERE user_id = :id")
    List<Ride> findAllByUserId(long id);

    @Insert
    Long insert(Ride ride);

    @Insert
    void insert(List<Ride> rides);

    @Update
    void update(Ride ride);

    @Delete
    int delete(List<Ride> rides);

    @Delete
    void delete(Ride ride);


}
