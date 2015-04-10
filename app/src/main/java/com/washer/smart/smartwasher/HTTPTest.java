package com.washer.smart.smartwasher;

import android.util.Log;

import model.LiveRecord;
import model.StartStatus;
import sdk.CallBack;
import sdk.WasherError;
import sdk.WasherService;

/**
 * Created by xxottosl on 2015-04-09.
 */
public class HTTPTest {


    public HTTPTest(){

        WasherService.startAt(-1, 45, new CallBack<StartStatus>() {
            @Override
            public void onSuccess(StartStatus startStatus) {

            }

            @Override
            public void onError(WasherError error) {

            }
        });

    }

}
