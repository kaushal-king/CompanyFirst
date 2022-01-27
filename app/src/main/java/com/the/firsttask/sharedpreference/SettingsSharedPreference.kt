package com.the.firsttask.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import com.the.firsttask.utils.ConstantHelper

class SettingsSharedPreference(ctx: Context) {
    var context: Context = ctx


    companion object {

        private var INSTANCE: SettingsSharedPreference? = null

        fun getInstance(context: Context): SettingsSharedPreference {
            return INSTANCE ?: synchronized(this) {
                var instance = SettingsSharedPreference(context)
                INSTANCE = instance
                INSTANCE as SettingsSharedPreference
            }
        }
    }

    fun setTheme(themeName: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            ConstantHelper.SHARED_PREFERENCE_ID,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString("themType", themeName)
        editor.apply()
    }

    fun getTheme(): String {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            ConstantHelper.SHARED_PREFERENCE_ID,
            Context.MODE_PRIVATE
        )
        val theme = sharedPreferences.getString("themType", ConstantHelper.THEME_NORMAL)
        return theme.toString()

    }


    fun setLanguage(languageName: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            ConstantHelper.SHARED_PREFERENCE_ID,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString("languageType", languageName)
        editor.apply()
    }

    fun getLanguage(): String {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            ConstantHelper.SHARED_PREFERENCE_ID,
            Context.MODE_PRIVATE
        )
        val language = sharedPreferences.getString("languageType", ConstantHelper.LANGUAGE_ENGLISH)
        return language.toString()

    }

    fun clearPreferences() {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            ConstantHelper.SHARED_PREFERENCE_ID,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}