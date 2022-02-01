package com.the.firsttask.database

import androidx.room.*

@Dao
interface AlarmDao
{
    @Query("Select * from AlarmInfo")
    fun getAllAlarm():List<AlarmEntity>

    @Query("DELETE FROM AlarmInfo WHERE id = :id")
    fun deleteAlarm(id:Int)

    @Insert
    fun addAlarm(alarmEntity: AlarmEntity)

    @Update
    fun updateAlarm(alarmEntity: AlarmEntity)


}