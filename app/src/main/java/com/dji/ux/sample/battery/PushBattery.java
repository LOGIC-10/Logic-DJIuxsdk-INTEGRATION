package com.dji.ux.sample.battery;

import android.content.Context;
import android.widget.Toast;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import dji.common.battery.BatteryState;
import dji.sdk.sdkmanager.DJISDKManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;




public class PushBattery {
    BatteryAttribute batteryAttribute = new BatteryAttribute();
    public  static String url = "http://10.199.157.176:8081/hello";

    public void  PushBatteryData(){
        try{
            DJISDKManager.getInstance().getProduct().getBattery().setStateCallback(new BatteryState.Callback() {
                @Override
                public void onUpdate(BatteryState djibatteryState) {
                    batteryAttribute.setVoltage(djibatteryState.getVoltage());
                    batteryAttribute.setCurrent(djibatteryState.getCurrent());
                    PushBattery.postHttp(batteryAttribute);
                }
            });

        }catch(Exception ignored){

        }
    }

    //类内函数
    //转换成json并且发送到服务器
    public static String postHttp(BatteryAttribute batteryAttribute) {
        String body = JSONObject.toJSONString(batteryAttribute);
        //定义返回的结果
        String mesg = null;
        //构建HttpClient实例
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);
        //构造Postmethod的实例
        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        Map<String, Object> map = JSONObject.parseObject(body,Map.class);
        Set<String> set = map.keySet();
        for (String s : set) {
            System.out.println(map.get(s).toString());
            postMethod.addParameter(s, map.get(s).toString());
        }
        try {
            //执行post请求
            httpClient.executeMethod(postMethod);
            //可以对响应回来的报文进行处理
            mesg = postMethod.getResponseBodyAsString();
            System.out.printf(mesg);
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //关闭连接释放资源的方法
            postMethod.releaseConnection();
            //((SimpleHttpConnectionManager)httpClient.getHttpConnectionManager()).shutdown();
            httpClient.getHttpConnectionManager().closeIdleConnections(0);
            return mesg;
        }
    }

}
