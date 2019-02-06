package com.intellisense.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by Habaiba Abbasi on 28/02/2017.
 */

public class Splash extends Base {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
   // long timeout = Long.MAX_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        // Font styling
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"Roboto-Thin.ttf",true);


        new Handler().postDelayed(new Runnable() {



            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(Splash.this, HomeActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
