package com.dji.ux.sample.battery;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import org.json.JSONTokener;

import dji.common.battery.BatteryState;
import dji.sdk.sdkmanager.DJISDKManager;

public class JsonHttp {
    BatteryAttribute batteryAttribute = new BatteryAttribute();
    public  static String url = "http://10.199.157.176:8081/AircraftInformation";

    public void PushJsonData(){
        try{
            DJISDKManager.getInstance().getProduct().getBattery().setStateCallback(new BatteryState.Callback() {
                @Override
                public void onUpdate(BatteryState djibatteryState) {
                    int voltage = djibatteryState.getVoltage();
                    int current = djibatteryState.getCurrent();
                    JSONObject batteryjson = new JSONObject();
                    batteryjson.put("voltage",voltage);
                    batteryjson.put("current",current);
                    Log.i("batteryjson:",batteryjson.toString());
                    url = url+"?info="+batteryjson.toString();

                    ConByGethttp conByGethttp = new ConByGethttp(url);
                    conByGethttp.start();

                }
            });

        }catch(Exception ignored){

        }
    }
}
