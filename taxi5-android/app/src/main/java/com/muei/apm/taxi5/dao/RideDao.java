package com.muei.apm.taxi5.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.muei.apm.taxi5.entities.Ride;

import java.util.List;

@Dao
public interface RideDao {


    @Query("SELECT * FROM user")
    List<Ride> getAll();

    @Query("SELECT * FROM ride WHERE rid IN (:rideIds)")
    List<Ride> loadAllByIds(long[] rideIds);


    @Query("SELECT * FROM ride WHERE user_id = :uid")
    List<Ride> findAllByUserId(long uid);

    @Insert
    public Long insert(Ride ride);

    @Insert
    public void insert(List<Ride> rides);

    @Update
    public void update(Ride ride);

    @Delete
    public int delete(List<Ride> rides);

    @Delete
    public void delete(Ride ride);


}
