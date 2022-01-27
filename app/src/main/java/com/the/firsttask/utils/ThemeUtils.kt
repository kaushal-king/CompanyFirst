package com.the.firsttask.utils

import android.app.Activity
import android.content.Intent
import android.util.Log

import com.the.firsttask.R
import com.the.firsttask.sharedpreference.SettingsSharedPreference


class ThemeUtils {


    companion object{
        lateinit var activity:Activity

        fun changeToTheme(activityName: Activity){
            activity=activityName
            activity.finish()
            activity.startActivity(Intent(activity, activity.javaClass))
        }

        fun onActivityCreateSetTheme(activity: Activity) {
            var theme= SettingsSharedPreference.getInstance(activity.applicationContext).getTheme()
            when (theme) {
                ConstantHelper.THEME_CHRISTMAS ->{
                    Log.e("theme", theme, )
                    activity.setTheme(R.style.Theme_Firsttask_NoActionBar_Christmas)}
                ConstantHelper.THEME_NORMAL ->{
                    Log.e("theme", theme, )
                    activity.setTheme(R.style.Theme_Firsttask_NoActionBar)
                }
                ConstantHelper.THEME_NIGHT -> {
                    Log.e("theme", theme, )
                    activity.setTheme(R.style.Theme_Firsttask_NoActionBar_Night)
                }
                else ->{
                    Log.e("theme", theme, )
                    activity.setTheme(R.style.Theme_Firsttask_NoActionBar)
                }
            }
        }
    }


}