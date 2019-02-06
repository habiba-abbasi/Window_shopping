package com.intellisense.shopping;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.intellisense.shopping.databinding.ActivityHomeBinding;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by Habaiba Abbasi on 01/03/2017.
 */

public class HomeActivity extends Base {

    ActivityHomeBinding binding;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private GoogleApiClient client;
    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_home);
       // setContentView(R.layout.activity_home);
        // Font styling
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"Roboto-Thin.ttf",true);

        if(Build.VERSION.SDK_INT>=21)
        {
            Window w=this.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
   /*     Button Shop_now_button = (Button) findViewById(R.id.button1);
        Button Find_store_button=(Button) findViewById(R.id.button2);
*/
       // Shop_now_button.setTextColor(Color.parseColor("#cd990b"));
        //Shop_now_button.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));


        //Find_store_button.setTextColor(Color.parseColor("#cd990b"));
      //  Find_store_button.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));
        binding.button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Intent intent = new Intent(getApplicationContext(),CategoryActivity.class);
                startActivity(intent);
            }

        });
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PaymentActivity.class);
                startActivity(intent);
            }
        });


    }



}
