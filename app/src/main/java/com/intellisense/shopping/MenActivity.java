package com.intellisense.shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.intellisense.shopping.Adapter.MenClothAdapter;
import com.intellisense.shopping.Models.MenModel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Habaiba Abbasi on 07/02/2017.
 */

public class MenActivity extends Base {


    ArrayList<MenModel> menModelList;
    MenClothAdapter adapter;
    private static GridView listView_top;
    TextView SelectedOpt ;
    String MID;
    String T_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_men);
        menModelList = new ArrayList<MenModel>();
        Intent intent = getIntent();
        SelectedOpt = (TextView) findViewById(R.id.selected);
        SelectedOpt.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));

        if(Build.VERSION.SDK_INT>=21)
        {
            Window w=this.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }



        MID = intent.getStringExtra("MID");
        T_NAME = intent.getStringExtra("CAT");



        switch (MID) {

            case "1":
                SelectedOpt.setText("MEN-TOPS");
                break;
            case "2":
                SelectedOpt.setText("MEN-TSHIRTS");
                break;
            case "3":
                SelectedOpt.setText("MEN-SHIRTS");
                break;
            case "4":
                SelectedOpt.setText("MEN-JEANS");
                break;
            case "5":
                SelectedOpt.setText("MEN-CHINOS");
                break;
            case "6":
                SelectedOpt.setText("MEN-BLAZERS");
                break;
            case "7":
                SelectedOpt.setText("MEN-KURTA KAMEEZ");
                break;
            case "8":
                SelectedOpt.setText("MEN-ACCESSORIES");
                break;

        }
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        if (connected) {


            new JSONAsyncTask().execute("http://www.icoderslab.com/Api/ShoppingApp/displayMenSimple.php?CID=" + MID);


        // GridView listView_top = (GridView) findViewById(R.id.listtop);
        adapter = new MenClothAdapter(getApplicationContext(), R.layout.activity_men_list, menModelList);
        listView_top = (GridView) findViewById(R.id.listtop);
        listView_top.setAdapter(adapter);
        //listView_top.;
        listView_top.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), MenDetailed.class);
                intent.putExtra("PID", "" + menModelList.get(position).getP_ID());
                intent.putExtra("MID", "" + MID);
                intent.putExtra("T_NAME", "" + T_NAME);

                startActivity(intent);
                // Toast.makeText(getApplicationContext(), menModelList.get(position).getPro_name(), Toast.LENGTH_LONG).show();
            }
        });

      }
        else
                {
                Toast.makeText(getApplicationContext(), "Kindly Turn on your Internet!", Toast.LENGTH_LONG).show();
                }


                Button back_button  = (Button) findViewById(R.id.backBt);
        Button home_button=(Button) findViewById(R.id.homeBt);

        back_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                /*Intent intent=new Intent(getApplicationContext(),MenMenuActivity.class);
                startActivity(intent);*/
                onBackPressed();

            }

        });

       home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });




    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressBar dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = (ProgressBar) findViewById(R.id.prog);
            dialog.setVisibility(View.VISIBLE);
        }


        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    JSONObject obj = new JSONObject(data);
                    JSONArray parentArray = obj.getJSONArray("clothes");



                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);

                        MenModel menModel =new MenModel();

                        menModel.setP_ID(finalObject.getString("P_ID"));

                        String name=finalObject.getString("pro_name");
                        String org_name[]=name.split("_");
                        String proname="";
                        for(int o=0;o<org_name.length;o++)
                        {
                            org_name[o]=org_name[o].replaceFirst( org_name[o].substring(0,1),org_name[o].substring(0,1).toUpperCase());
                            proname+=org_name[o];

                            if (o == (org_name.length)-1)
                                break;
                            proname+=" ";
                        }
                        menModel.setPro_name(proname);

                         //  menModel.setSize(finalObject.getString("size"));
                        //  menModel.setColor(finalObject.getString("color"));

                        menModel.setP_PRICE(finalObject.getString("P_PRICE"));
                        String ur= finalObject.getString("images");
                        ur=ur.replaceAll("\\/","/");
                        String Actual_hehe="http://icoderslab.com/Api/ShoppingApp/";
                        Actual_hehe+=ur;
                        menModel.setI_Url(Actual_hehe);

                        //menModel.setStock(finalObject.getString("Stock"));

                        //adding object in the list
                        menModel.setcombolopia(T_NAME);
                        menModelList.add(menModel);

                    }
                    return true;
                }

                //------------------>>

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }



}
