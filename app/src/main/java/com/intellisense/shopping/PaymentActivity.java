package com.intellisense.shopping;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.intellisense.shopping.databinding.ActivityPaymentBinding;

import me.anwarshahriar.calligrapher.Calligrapher;

public class PaymentActivity extends Activity {

    ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_payment);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_payment);

        // Font styling
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"Roboto-Thin.ttf",true);

        if(Build.VERSION.SDK_INT>=21)
        {
            Window w=this.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(this.getResources().getColor(R.color.place_autocomplete_search_text));
        }

        binding.PayMethod.setPaintFlags(binding.PayMethod.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        binding.Bt2.setOnClickListener( new  View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {


                                                Intent intent = new Intent(getApplicationContext(), OnlinePaymentActivity.class);
                                                startActivity(intent);
                                            }
                                        }
        );



        }
}
