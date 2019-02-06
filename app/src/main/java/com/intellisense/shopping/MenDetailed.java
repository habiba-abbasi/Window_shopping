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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.intellisense.shopping.Adapter.MenAdapterDetailed;
import com.intellisense.shopping.Models.MenDetailedModel;

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
import java.util.List;

/**
 * Created by Habiba Abbasi on 28/02/2017.
 */

public class MenDetailed extends Base {

    ArrayList<MenDetailedModel> menDetailedModelList;
    MenAdapterDetailed adapter;
    String PID="";
    String CID="";
    TextView SelectedOpt ;
    private static GridView Detailed_View_top;
    String T_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_men);
        SelectedOpt=(TextView) findViewById(R.id.selected);
        SelectedOpt.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));

        if(Build.VERSION.SDK_INT>=21)
        {
            Window w=this.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        Button back_button  = (Button) findViewById(R.id.button1);
        Button home_button=(Button) findViewById(R.id.button2);

        back_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
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

        Intent intent=getIntent();
        PID=intent.getStringExtra("PID");
        CID=intent.getStringExtra("MID");
        T_name=intent.getStringExtra("T_NAME");

        switch(CID){

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
        menDetailedModelList = new ArrayList<MenDetailedModel>();


        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        if (connected) {

            new JSONAsyncTask().execute("http://www.icoderslab.com/Api/ShoppingApp/getMenClothes.php?CID="+CID);

        adapter= new MenAdapterDetailed(getApplicationContext(), R.layout.activity_detail_men_showdata, menDetailedModelList);
        Detailed_View_top  = (GridView) findViewById(R.id.frame);

        Detailed_View_top.setAdapter(adapter);

    }
        else
    {
        Toast.makeText(getApplicationContext(), "Kindly Turn on your Internet!", Toast.LENGTH_LONG).show();
    }
    }
    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressBar dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = (ProgressBar) findViewById(R.id.pg1);
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
                        MenDetailedModel menTopModel=new MenDetailedModel();
                        //menTopModel.setStock(finalObject.getString("Stock"));

                        //adding object in the list
                        String bufferPID=finalObject.getString("P_ID");
                        if(PID.contains(bufferPID))
                        {
                            menTopModel.setP_ID(bufferPID);

                            String name=finalObject.getString("pro_name");
                            String org_name[]=name.split("_");
                            String proname="";
                            for(int o=0;o<org_name.length;o++)
                            {
                                org_name[o]=org_name[o].replaceFirst( org_name[o].substring(0,1),org_name[o].substring(0,1).toUpperCase());
                                proname+=org_name[o];
                                proname+=" ";
                            }
                            menTopModel.setPro_name(proname);
                            //menTopModel.setSize(finalObject.getString("size"));
                            //menTopModel.setColor(finalObject.getString("color"));
                            menTopModel.setP_PRICE(finalObject.getString("P_PRICE"));

                            List<MenDetailedModel.Color> cl = new ArrayList<MenDetailedModel.Color>();
                            for(int j=0;j<finalObject.getJSONArray("color").length();j++)
                            {
                                // JSONObject colorObject = finalObject.getJSONArray("color").getJSONObject(j);
                                MenDetailedModel.Color colors=new MenDetailedModel.Color();
                                colors.setName(finalObject.getJSONArray("color").getJSONObject(j).getString("color"));
                                cl.add(colors);
                            }
                            menTopModel.setColorlist(cl);
                            List<MenDetailedModel.Size> sl = new ArrayList<MenDetailedModel.Size>();
                            for(int j=0;j<finalObject.getJSONArray("size").length();j++)
                            {
                                // JSONObject colorObject = finalObject.getJSONArray("color").getJSONObject(j);
                                MenDetailedModel.Size sizes=new MenDetailedModel.Size();
                                sizes.setSize(finalObject.getJSONArray("size").getJSONObject(j).getString("size"));
                                sl.add(sizes);
                            }
                            menTopModel.setSizelist(sl);
                            //Images
                            List<MenDetailedModel.Images> Imagesl = new ArrayList<MenDetailedModel.Images>();
                            for(int j=0;j<finalObject.getJSONArray("images").length();j++)
                            {
                                // JSONObject colorObject = finalObject.getJSONArray("color").getJSONObject(j);
                                MenDetailedModel.Images Imagess=new MenDetailedModel.Images();
                                Imagess.setImages(finalObject.getJSONArray("images").getJSONObject(j).getString("img"));
                                Imagesl.add(Imagess);
                            }
                            menTopModel.setImageslist(Imagesl);
                            menTopModel.setRb((Float.parseFloat(finalObject.getString("Popularity"))));
                            menTopModel.setcombolopia(T_name);
                            menDetailedModelList.add(menTopModel);
                            return true;
                        }

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
                Toast.makeText(getApplicationContext(), "kindly! Turn on your Internet", Toast.LENGTH_LONG).show();
        }
    }
}
