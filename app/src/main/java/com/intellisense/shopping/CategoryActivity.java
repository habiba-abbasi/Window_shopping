package com.intellisense.shopping;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.databinding.DataBindingUtil;

import com.intellisense.shopping.databinding.ActivityCategoryBinding;

import me.anwarshahriar.calligrapher.Calligrapher;


/**
 * Created by Habaiba Abbasi on 30/01/2017.
 */

public class CategoryActivity extends Base {

    ActivityCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_category);
        //setContentView(R.layout.activity_category);
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



        //set button working
        binding.menBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MenMenuActivity.class);
                startActivity(intent);
            }
        });

       binding.womenBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WomenMenu.class);
                startActivity(intent);
            }
        });

        binding.childBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ChildMenuActivity.class);
                startActivity(intent);
            }
        });


    }





}
