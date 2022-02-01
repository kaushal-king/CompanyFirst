package com.the.firsttask.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "AlarmInfo")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    val repeat: Boolean,
    val date:String,
    val time:String,
    val description:String,

)


