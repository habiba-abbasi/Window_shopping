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

import com.intellisense.shopping.Adapter.WomenClothAdapter;
import com.intellisense.shopping.Models.WomenModel;

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

public class Women extends Base {


    ArrayList<WomenModel> womenTopModelList;
    WomenClothAdapter adapter;
    private static GridView Women_List;
    TextView SelectedOpt ;
    String OPT_ID="";
    String T_NAME="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_women);
        womenTopModelList= new ArrayList<WomenModel>();
        Intent intent=getIntent();
        SelectedOpt=(TextView) findViewById(R.id.changetype);
        SelectedOpt.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));

        if(Build.VERSION.SDK_INT>=21)
        {
            Window w=this.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(this.getResources().getColor(R.color.women_activity_Layout_color));
        }


        OPT_ID= intent.getStringExtra("OPT_ID");
        T_NAME = intent.getStringExtra("CAT");


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
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        if (connected) {


            new JSONAsyncTask().execute("http://www.icoderslab.com/Api/ShoppingApp/displayWomenSimple.php?CID="+OPT_ID);

        // GridView listView_top = (GridView) findViewById(R.id.listtop);
        adapter= new WomenClothAdapter(getApplicationContext(), R.layout.activity_women_list, womenTopModelList);
        Women_List  = (GridView) findViewById(R.id.womenlist);
        Women_List.setAdapter(adapter);
        Women_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(getApplicationContext(),WomenDetailed.class);
                intent.putExtra("PID",""+womenTopModelList.get(position).getP_ID());
                intent.putExtra("OPT_ID",""+OPT_ID);
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

                        WomenModel womenTopModel=new WomenModel();

                        womenTopModel.setP_ID(finalObject.getString("P_ID"));
                        String name=finalObject.getString("pro_name");
                        String name_arr[]=name.split("_");
                        String org_name="";
                        for(int s=0;s<name_arr.length;s++){

                            name_arr[s]=name_arr[s].replaceFirst(name_arr[s].substring(0,1),name_arr[s].substring(0,1).toUpperCase());
                            org_name+=name_arr[s];
                            if(s == (name_arr.length)-1)
                                break;
                            org_name+=" ";

                        }

                        womenTopModel.setPro_name(org_name);

                        // womenTopModel.setSize(finalObject.getString("size"));
                        // womenTopModel.setColor(finalObject.getString("color"));
                        womenTopModel.setP_PRICE(finalObject.getString("P_PRICE"));
                        String ur= finalObject.getString("images");
                        ur=ur.replaceAll("\\/","/");
                        String Actual_hehe="http://icoderslab.com/Api/ShoppingApp/";
                        Actual_hehe+=ur;
                        womenTopModel.setI_Url(Actual_hehe);

                        //menTopModel.setStock(finalObject.getString("Stock"));

                        //adding object in the list
                        womenTopModel.setWomenCatType(T_NAME);
                        womenTopModelList.add(womenTopModel);

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
