package com.the.firsttask.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import com.the.firsttask.sharedpreference.SettingsSharedPreference
import java.util.*

class LanguageUtils {

    companion object {
        lateinit var activity: Activity

        fun changeLanguage(activityName: Activity) {
            activity = activityName
            activity.finish()
            activity.startActivity(Intent(activity, activity.javaClass))
        }

        fun setLocale(context: Context){

            var lang=SettingsSharedPreference.getInstance(context).getLanguage()
            val locale = Locale(lang)
            Locale.setDefault(locale)
            val resources: Resources = context.resources
            val configuration: Configuration = resources.getConfiguration()
            configuration.setLocale(locale)
            resources.updateConfiguration(configuration, resources.getDisplayMetrics())

        }
    }
}