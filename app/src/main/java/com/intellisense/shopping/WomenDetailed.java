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

import com.intellisense.shopping.Adapter.WomenClothDetailedAdapter;
import com.intellisense.shopping.Models.WomenDetailedModel;

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

public class WomenDetailed extends Base {


    ArrayList<WomenDetailedModel> womenTopDetailedModelList;
    WomenClothDetailedAdapter adapter;
    String PID="";
    String OPT_ID="";
    String T_name="";
    TextView SelectedOpt ;
    private static GridView women_listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_women_detailed);
        SelectedOpt=(TextView) findViewById(R.id.categorydetailtype);
        SelectedOpt.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));

        if(Build.VERSION.SDK_INT>=21)
        {
            Window w=this.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(this.getResources().getColor(R.color.women_activity_Layout_color));
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
        OPT_ID=intent.getStringExtra("OPT_ID");
        T_name=intent.getStringExtra("T_NAME");

        switch(OPT_ID){

            case "1":
                SelectedOpt.setText("WOMEN-TOPS");
                break;
            case "2":
                SelectedOpt.setText("WOMEN-TSHIRTS");
                break;
            case "3":
                SelectedOpt.setText("WOMEN-SHIRTS");
                break;
            case "4":
                SelectedOpt.setText("WOMEN-JEANS");
                break;
            case "5":
                SelectedOpt.setText("WOMEN-CHINOS");
                break;
            case "6":
                SelectedOpt.setText("WOMEN-BLAZERS");
                break;
            case "7":
                SelectedOpt.setText("WOMEN-KURTA KAMEEZ");
                break;
            case "8":
                SelectedOpt.setText("WOMEN-ACCESSORIES");
                break;
            default:{}

        }

        womenTopDetailedModelList= new ArrayList<WomenDetailedModel>();

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        if (connected) {
        new JSONAsyncTask().execute("http://www.icoderslab.com/Api/ShoppingApp/getWomean.php?CID="+OPT_ID);

        adapter= new WomenClothDetailedAdapter(getApplicationContext(), R.layout.activity_detailed_women_showdata, womenTopDetailedModelList);
        women_listView  = (GridView) findViewById(R.id.womenframe);

        women_listView.setAdapter(adapter);



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
                        WomenDetailedModel womenTopModel=new WomenDetailedModel();
                        //menTopModel.setStock(finalObject.getString("Stock"));

                        //adding object in the list
                        String bufferPID=finalObject.getString("P_ID");
                        if(PID.contains(bufferPID))
                        {
                            womenTopModel.setP_ID(bufferPID);

                            String name=finalObject.getString("pro_name");
                            String Org_name[]=name.split("_");
                            String proname="";
                            for(int o=0;o<Org_name.length;o++)
                            {
                                Org_name[o]=Org_name[o].replaceFirst( Org_name[o].substring(0,1),Org_name[o].substring(0,1).toUpperCase());
                                proname+=Org_name[o];
                                proname+=" ";
                            }

                            womenTopModel.setPro_name(proname);
                            womenTopModel.setWomenCatType(T_name);
                            womenTopModel.setP_PRICE(finalObject.getString("P_PRICE"));

                            List<WomenDetailedModel.Color> cl = new ArrayList<WomenDetailedModel.Color>();
                            for(int j=0;j<finalObject.getJSONArray("color").length();j++)
                            {
                                // JSONObject colorObject = finalObject.getJSONArray("color").getJSONObject(j);
                                WomenDetailedModel.Color colors=new WomenDetailedModel.Color();
                                colors.setName(finalObject.getJSONArray("color").getJSONObject(j).getString("color"));
                                cl.add(colors);
                            }
                            womenTopModel.setColorlist(cl);
                            List<WomenDetailedModel.Size> sl = new ArrayList<WomenDetailedModel.Size>();
                            for(int j=0;j<finalObject.getJSONArray("size").length();j++)
                            {
                                // JSONObject colorObject = finalObject.getJSONArray("color").getJSONObject(j);
                                WomenDetailedModel.Size sizes=new WomenDetailedModel.Size();
                                sizes.setSize(finalObject.getJSONArray("size").getJSONObject(j).getString("size"));
                                sl.add(sizes);
                            }
                            womenTopModel.setSizelist(sl);
                            //Images
                            List<WomenDetailedModel.Images> Imagesl = new ArrayList<WomenDetailedModel.Images>();
                            for(int j=0;j<finalObject.getJSONArray("images").length();j++)
                            {
                                // JSONObject colorObject = finalObject.getJSONArray("color").getJSONObject(j);
                                WomenDetailedModel.Images Imagess=new WomenDetailedModel.Images();
                                Imagess.setImages(finalObject.getJSONArray("images").getJSONObject(j).getString("img"));
                                Imagesl.add(Imagess);
                            }
                           womenTopModel.setImageslist(Imagesl);
                            womenTopModel.setRb((Float.parseFloat(finalObject.getString("Popularity"))));
                            womenTopDetailedModelList.add(womenTopModel);
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
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }
}
