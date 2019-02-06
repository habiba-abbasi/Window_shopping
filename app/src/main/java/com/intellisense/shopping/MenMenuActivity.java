package com.intellisense.shopping;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.intellisense.shopping.databinding.ActivityMenMenuBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by Habaiba Abbasi on 03/03/2017.
 */

public class MenMenuActivity extends Base {


    ActivityMenMenuBinding binding;
   // LinearLayout layout;
    static int total_button=10;
    Button[] button= new Button[total_button];
    // Font styling
    Calligrapher calligrapher;
    String answer[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_men_menu);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_men_menu);

        //set Font
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"Roboto-Thin.ttf",true);

        if(Build.VERSION.SDK_INT>=21)
        {
            Window w=this.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        /*Button back_button  = (Button) findViewById(R.id.button1);
        Button home_button=(Button) findViewById(R.id.button2);
        */
        binding.backBt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
               /* Intent intent=new Intent(getApplicationContext(),CategoryActivity.class);
                startActivity(intent);*/
                onBackPressed();

            }

        });
        binding.homeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);

            }
        });


        //layout = (LinearLayout) findViewById(R.id.LayoutForButtons);

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        if (connected) {
        new JSONTask().execute("http://icoderslab.com/Api/ShoppingApp/getCategory.php");

/*

        for(int i=0;i<total_button;i++){
            final int var = i;
            button[i] = new Button(this);

            button[i].setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 10.0f
            ));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button[i].getLayoutParams();
            params.setMargins(10, 25, 0, 0); //left, top, right, bottom
            button[i].setLayoutParams(params);

            //  button.setText(Cloth[i]);

            button[i].setTextSize(35);
            // button.setTextColor(0xcd990b);
            button[i].setPadding(10, 5, 5, 20);
            button[i].setTextColor(Color.parseColor("#cd990b"));

            button[i].setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));

            button[i].setBackgroundResource(R.drawable.button_4);
            button[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    gridButtonClicked(var);
                }

                private void gridButtonClicked(int var) {

                    switch (var){
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:

                        {
                            Intent intent = new Intent(getApplicationContext(),MenActivity.class);
                            intent.putExtra("CID",""+(var+1));
                           // intent.putExtra("T_Name",""+answer[var]);
                            startActivity(intent);

                            break;
                        }
                        default:{}

                    }

                }
            });
            layout.addView(button[i]);
        }*/
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Kindly Turn on your Internet!", Toast.LENGTH_LONG).show();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //   client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }





    public class JSONTask extends AsyncTask<String, String, String> {


       // ProgressBar dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*dialog = (ProgressBar) findViewById(R.id.prog);
            dialog.setVisibility(View.VISIBLE);
        */
        binding.prog.setVisibility(View.VISIBLE);}


        @Override
        protected String doInBackground(String... params) {
            // int i;
            HttpURLConnection connection = null;
            BufferedReader reader = null;


            try {

                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);

                }

                String finalJson = buffer.toString();
                JSONObject obj = new JSONObject(finalJson);
                JSONArray parentArray = obj.getJSONArray("clothes");
                StringBuffer finalBufferData = new StringBuffer();

                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    String cloth = finalObject.getString("C_NAME");
                    cloth = cloth.replaceFirst(cloth.substring(0, 1), cloth.substring(0, 1).toUpperCase());
                    finalBufferData.append(cloth + ".");
                }



                return finalBufferData.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        // @TargetApi(Build.VERSION_CODES.M)
        protected void onPostExecute(String result) {
            //dialog.setVisibility(View.GONE);
            binding.prog.setVisibility(View.GONE);
            super.onPostExecute(result);
            calligrapher = new Calligrapher(MenMenuActivity.this);

            answer = result.split("\\.");
            for(int i=0;i<answer.length;i++){
                final int var = i;
                button[i] = new Button(MenMenuActivity.this);
                button[i].setText(answer[i]);
                calligrapher.setFont(MenMenuActivity.this,"Roboto-Thin.ttf",true);
                button[i].setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 10.0f
                ));
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button[i].getLayoutParams();
                params.setMargins(10, 25, 0, 0); //left, top, right, bottom
                button[i].setLayoutParams(params);

                //  button.setText(Cloth[i]);

                button[i].setTextSize(35);
                // button.setTextColor(0xcd990b);
                button[i].setPadding(10, 5, 5, 20);
                button[i].setTextColor(Color.parseColor("#cd990b"));

               // button[i].setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));

                button[i].setBackgroundResource(R.drawable.button_4);
                button[i].setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(var);
                    }

                    private void gridButtonClicked(int var) {

                        switch (var){
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:

                            {
                                Intent intent = new Intent(getApplicationContext(),MenActivity.class);
                                intent.putExtra("MID",""+(var+1));
                                intent.putExtra("CAT",""+answer[var]);
                                startActivity(intent);

                                break;
                            }


                        }

                    }
                });
                binding.LayoutForButtons.addView(button[i]);


            }



        }

    }



}
