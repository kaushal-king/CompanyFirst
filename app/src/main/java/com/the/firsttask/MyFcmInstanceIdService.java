package com.the.firsttask;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;


public class MyFcmInstanceIdService extends FirebaseMessagingService {

//    @Override
//    public void onTokenRefresh() {
//        String recent_token= FirebaseInstanceId.getInstance().getToken();
//        SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF),Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=sharedPreferences.edit();
//        editor.putString(getString(R.string.FCM_TOKEN),recent_token);
//        editor.commit();
//    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        sendNewTokenToServer(s);
    }

    private void sendNewTokenToServer(String s) {
        Log.d("TOKEN",String.valueOf(s));

    }
}
