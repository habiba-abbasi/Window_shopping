
package com.intellisense.shopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.intellisense.shopping.Adapter.ChildClothAdapter;
import com.intellisense.shopping.Models.ChildModel;
import com.intellisense.shopping.databinding.ActivityChildBinding;

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

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by Habiba Abbasii on 14/03/2017.
 */

public class ChildActivity extends Activity{
    ActivityChildBinding binding;
    ArrayList<ChildModel> childModelList;
    ChildClothAdapter adapter;
    //private static GridView listView_top;
    //TextView SelectedOpt ;
    String CID="";
    String T_NAME="";
    // Font styling
    Calligrapher calligrapher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_child);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_child);

        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"Roboto-Thin.ttf",true);
        //calligrapher = new Calligrapher(ChildActivity.this);

        childModelList= new ArrayList<ChildModel>();
        Intent intent= getIntent();
        //SelectedOpt=(TextView) findViewById(R.id.selected);
        //SelectedOpt.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));

        if(Build.VERSION.SDK_INT>=21)
        {
            Window w=this.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(this.getResources().getColor(R.color.child_activity_Layout_color));
        }

        CID = intent.getStringExtra("CID");
        T_NAME=intent.getStringExtra("CAT");
        switch(CID){

            case "1":
                binding.tvCat.setText("CHILDREN-TOPS");
                break;
            case "2":
                binding.tvCat.setText("CHILDREN-TSHIRTS");
                break;
            case "3":
                binding.tvCat.setText("CHILDREN-SHIRTS");
                break;
            case "4":
                binding.tvCat.setText("CHILDREN-JEANS");
                break;
            case "5":
                binding.tvCat.setText("CHILDREN-CHINOS");
                break;
            case "6":
                binding.tvCat.setText("CHILDREN-BLAZERS");
                break;
            case "7":
                binding.tvCat.setText("CHILDREN-KURTA KAMEEZ");
                break;
            case "8":
                binding.tvCat.setText("CHILDREN-ACCESSORIES");
                break;
            default:{}

        }


        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        if (connected) {

            new JSONAsyncTask().execute("http://www.icoderslab.com/Api/ShoppingApp/displayChildrenSimple.php?CID="+CID);

            // GridView listView_top = (GridView) findViewById(R.id.listtop);
            adapter= new ChildClothAdapter(getApplicationContext(), R.layout.activity_child_list, childModelList);
            //listView_top  = (GridView) findViewById(R.id.listtop);


            binding.listtop.setAdapter(adapter);
            binding.listtop.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position,
                                        long id) {
                    // TODO Auto-generated method stub
                    Intent intent=new Intent(getApplicationContext(),ChildDetailedActivity.class);
                    intent.putExtra("PID",""+childModelList.get(position).getP_ID());
                    intent.putExtra("CID",""+CID);
                    intent.putExtra("T_NAME", "" + T_NAME);

                    startActivity(intent);
                    // Toast.makeText(getApplicationContext(), menTopModelList.get(position).getPro_name(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Kindly Turn on your Internet!", Toast.LENGTH_LONG).show();
        }


        /*Button back_button  = (Button) findViewById(R.id.backBt);
        Button home_button=(Button) findViewById(R.id.button2);
*/
        binding.backBt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
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




    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        //ProgressBar dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //dialog = (ProgressBar) findViewById(R.id.pg1);
            binding.progBar.setVisibility(View.VISIBLE);
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

                        ChildModel childModel = new ChildModel();

                        childModel.setP_ID(finalObject.getString("P_ID"));
                        String name=finalObject.getString("pro_name");
                        String name_arr[]=name.split("_");
                        String org_name="";
                        for(int s=0;s<name_arr.length;s++){
                            name_arr[s]=name_arr[s].replaceFirst(name_arr[s].substring(0,1),name_arr[s].substring(0,1).toUpperCase());
                            org_name+=name_arr[s];

                            if (s == (name_arr.length)-1)
                                break;

                            org_name+=" ";

                        }
                        childModel.setPro_name(org_name);
                        //   menTopModel.setSize(finalObject.getString("size"));
                        //  menTopModel.setColor(finalObject.getString("color"));
                        childModel.setP_PRICE(finalObject.getString("P_PRICE"));
                        String ur= finalObject.getString("images");
                        ur=ur.replaceAll("\\/","/");
                        String Actual_hehe="http://icoderslab.com/Api/ShoppingApp/";
                        Actual_hehe+=ur;
                        childModel.setCatType(T_NAME);
                        childModel.setI_Url(Actual_hehe);

                        //menTopModel.setStock(finalObject.getString("Stock"));

                        //adding object in the list
                        childModelList.add(childModel);

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
            binding.progBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }

}
