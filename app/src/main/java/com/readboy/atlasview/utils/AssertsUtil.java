package com.readboy.atlasview.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AssertsUtil {
    public static String getFromAssets(Context context,String fileName) {
        StringBuilder Result = new StringBuilder();
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                Result.append(line);
            return Result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.toString();
    }
}
