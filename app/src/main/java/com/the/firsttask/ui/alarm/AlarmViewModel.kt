package com.the.firsttask.ui.alarm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.the.firsttask.database.AlarmEntity
import com.the.firsttask.database.MovieEntity
import com.the.firsttask.database.RoomDb


class AlarmViewModel(application: Application):AndroidViewModel(application) {
    var allAlarm: MutableLiveData<List<AlarmEntity>> = MutableLiveData()
    init {
        getAllAlarm()
    }
    fun getAllAlarmObservers(): MutableLiveData<List<AlarmEntity>> {
        return allAlarm
    }

    fun getAllAlarm() {
        val alarmDao = RoomDb.getAppDatabase((getApplication()))?.alarmDao()
        val list = alarmDao?.getAllAlarm()
        allAlarm.postValue(list!!)

    }


    fun insertAlarm(entity: AlarmEntity) {
        val alarmDao = RoomDb.getAppDatabase((getApplication()))?.alarmDao()
        alarmDao?.addAlarm(entity)
        getAllAlarm()
    }


    fun deleteAlarm(id:Int) {
        val alarmDao = RoomDb.getAppDatabase((getApplication()))?.alarmDao()
        alarmDao?.deleteAlarm(id)
        getAllAlarm()
    }

    fun updateAlarm(entity: AlarmEntity) {
        val alarmDao = RoomDb.getAppDatabase((getApplication()))?.alarmDao()
        alarmDao?.updateAlarm(entity)
        getAllAlarm()
    }
}