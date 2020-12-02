package com.readboy.atlasview.bean;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MyObject {
    public static final String TAG = MyObject.class.getSimpleName() ;
    private Context mContext;
    private String fileName;
    public MyObject(Context context,String fileName){
        this.fileName = fileName;
        mContext = context;
    }

    /**
     * 获取person字符串传Html
     * @return
     */
    @JavascriptInterface
    public String getData(String fileName){
       String json = getFromAssets(this.fileName);
       Log.d("TAG","current json is " + json);
        return json;
    }

    public String getFromAssets(String fileName) {
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(mContext.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }

}

