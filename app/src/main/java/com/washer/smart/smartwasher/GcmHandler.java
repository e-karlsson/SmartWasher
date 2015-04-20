package com.washer.smart.smartwasher;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import extra.Storage;
import model.Status;
import sdk.CallBack;
import sdk.WasherError;
import sdk.WasherService;

/**
 * Created by xxottosl on 2015-04-20.
 */
public class GcmHandler {

    private static Activity activity;
    private static GoogleCloudMessaging gcm;
    private static String SENDER_ID = "89473749364";
    private static String regid;

    public static void handle(Activity activity) {
        GcmHandler.activity = activity;

        Log.d("GCM", "Register 0");
        if (checkPlayServices()) {
            Log.d("GCM", "Register 1");
            gcm = GoogleCloudMessaging.getInstance(activity);
            regid = getRegistrationId(activity);

            if (regid.isEmpty()) {
                Log.d("GCM", "Register 2");
                registerInBackground();
            }else if(!Storage.loadBoolean(Storage.SENT_ID, false)){
                sendRegistrationIdToBackend();
            }
        }
    }


    private static boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                        9000).show();
            } else {
                Log.i("GCM ERROR", "This device is not supported.");

            }
            return false;
        }
        return true;
    }

    private static void storeRegistrationId(Context context, String regId) {

        int appVersion = getAppVersion(context);

        Storage.saveString(Storage.REG_ID, regId);
        Storage.saveInt(Storage.APP_VER, appVersion);

    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }

    }

    private static void registerInBackground() {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object... params) {

                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(activity);
                    }
                    regid = gcm.register(SENDER_ID);

                    Log.d("REGID", ""+regid);

                    Storage.saveString(Storage.REG_ID, regid);
                    // You should send the registration ID to your server over HTTP,
                    // so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the registration ID - no need to register again.

                } catch (IOException ex) {
                    Log.d("GCM", "Error 1 "+ex.getMessage());
                }
                return null;
            }



        }.execute(null, null, null);

    }

    private static void sendRegistrationIdToBackend(){
        WasherService.register(regid, new CallBack<Status>() {
            @Override
            public void onSuccess(Status status) {
                if(status.success()){
                    storeRegistrationId(activity, regid);
                    Storage.saveBoolean(Storage.SENT_ID, true);
                }else{

                }
            }

            @Override
            public void onError(WasherError error) {

            }
        });
    }

    private static String getRegistrationId(Context context) {
        String registrationId = Storage.loadString(Storage.REG_ID,"");
        if (registrationId.isEmpty()) {

            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing registration ID is not guaranteed to work with
        // the new app version.
        int registeredVersion = Storage.loadInt(Storage.APP_VER, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {

            return "";
        }
        return registrationId;
    }


}