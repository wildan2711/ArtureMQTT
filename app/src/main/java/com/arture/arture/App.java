package com.arture.arture;

import android.app.Application;

import model.Plant;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by wildan on 8/14/2016.
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Prototype.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
