package com.meridian.feedback;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

/**
 * Created by Anvin on 5/18/2017.
 */

public class LanguageCheck extends AppCompatActivity {

    public LanguageCheck(Context appContext){
        SharedPreferences myPrefs = appContext.getSharedPreferences("LangPref", Context.MODE_PRIVATE);
        try {
            String language = myPrefs.getString("language", null);
            System.out.println("language in lnguagecheck : "+language);
            if(language!=null||language!="") {
                Locale locale2 = new Locale(language);
                Locale.setDefault(locale2);
                Configuration config2 = new Configuration();
                config2.locale = locale2;
                appContext.getResources().updateConfiguration(config2, null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*@Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (locale2 != null){
            newConfig.locale = locale2;
            Locale.setDefault(locale2);
            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        }
    }*/
}
