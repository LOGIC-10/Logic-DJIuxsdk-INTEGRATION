package com.dji.ux.sample.battery;

import org.apache.commons.httpclient.HttpConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ConByGethttp extends Thread{
    String urlstr = null;
    public ConByGethttp(String url){
        this.urlstr = url;
    }
    @Override
    public void run(){
        HttpURLConnection connection = null;
        try{
            //创建客户端对象
            URL url = new URL(urlstr);
            //创建链接对象
            connection = (HttpURLConnection) url.openConnection();
            //发起链接
            connection.connect();
            //获取服务器响应数据
            String lines = null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((lines=bufferedReader.readLine())!=null){

            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            //关闭链接
            if(connection!=null){
                connection.disconnect();
            }

        }

    }
}
