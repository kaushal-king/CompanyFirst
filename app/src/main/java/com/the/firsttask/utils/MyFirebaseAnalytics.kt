package com.the.firsttask.utils

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

object MyFirebaseAnalytics{

    var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    fun addScreenView(screenName:String,screenClass:String){
             firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                 param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
                 param(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
             }

         }
}


