package com.washer.smart.smartwasher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import extra.LiveData;
import extra.Storage;
import model.LiveRecord;
import sdk.CallBack;
import sdk.WasherError;
import sdk.WasherService;

/**
 * Created by xxottosl on 2015-04-20.
 */
public class SplashActivity extends Activity {

    private ImageView imageView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = (ImageView) findViewById(R.id.iv_splash);
        WasherService.init("http://kexdns.ddns.net", 8080);
        Storage.init(this);
        context = this;

        WasherService.getLiveRecord(new CallBack<LiveRecord>() {
            @Override
            public void onSuccess(LiveRecord liveRecord) {
                LiveData.setLiveRecord(liveRecord);
            }

            @Override
            public void onError(WasherError error) {

            }
        });

        new SplashThread().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
    }




    class SplashThread extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            for(int i = 0; i < 3; ++i){


                publishProgress(i);

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int index = values[0];
            switch (index) {
                case 0: imageView.setImageDrawable(getResources().getDrawable(R.mipmap.splash_2)); break;
                case 1: imageView.setImageDrawable(getResources().getDrawable(R.mipmap.splash_3)); break;
                case 2: imageView.setImageDrawable(getResources().getDrawable(R.mipmap.splash_4)); break;
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
