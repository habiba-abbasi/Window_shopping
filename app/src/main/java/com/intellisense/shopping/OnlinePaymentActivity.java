package com.intellisense.shopping;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.intellisense.shopping.databinding.ActivityOnlinePaymentBinding;

import me.anwarshahriar.calligrapher.Calligrapher;

public class OnlinePaymentActivity extends Activity {

    ActivityOnlinePaymentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_online_payment);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_online_payment);

        // Font styling
        final Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"Roboto-Thin.ttf",true);

        if(Build.VERSION.SDK_INT>=21)
        {
            Window w=this.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(this.getResources().getColor(R.color.place_autocomplete_search_text));
        }
        binding.doneBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_thankyou);
                calligrapher.setFont(OnlinePaymentActivity.this,"Roboto-Thin.ttf",true);

            }
        });
    }
}
