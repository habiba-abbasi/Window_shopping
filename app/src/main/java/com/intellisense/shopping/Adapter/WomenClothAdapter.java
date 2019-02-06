package com.intellisense.shopping.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intellisense.shopping.Models.WomenModel;
import com.intellisense.shopping.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Habaiba Abbasi on 03/03/2017.
 */

public class WomenClothAdapter extends ArrayAdapter<WomenModel> {

    ArrayList<WomenModel> womenTopModelsList;
    int resources;
    LayoutInflater inflater;
    ViewHolder holder;

    public WomenClothAdapter(Context context, int resource, ArrayList<WomenModel> objects) {
        super(context, resource, objects);
        womenTopModelsList=objects;
        this.resources=resource;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//convertview=design
        View v = convertView;
        if (v == null){
            holder = new ViewHolder();
            v = inflater.inflate(resources , null);
            holder.image=(ImageView) v.findViewById(R.id.imageView);
            holder.color =(TextView) v.findViewById(R.id.colorView);
            holder.priceView=(TextView)v.findViewById(R.id.pricetView);
            holder.displayType=(TextView)v.findViewById(R.id.womenmenutype);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }


        // Then later, when you want to display image
        // ImageLoader.getInstance().displayImage(menTopModelsList.get(position).getI_Url(), holder.image); // Default options will be used
        //  holder.image.setImageResource(R.drawable.ic_launcher);
        new DownloadImageTask(holder.image).execute(womenTopModelsList.get(position).getI_Url());
        holder.color.setText(womenTopModelsList.get(position).getPro_name ());
        holder.priceView.setText("Rs."+womenTopModelsList.get(position).getP_PRICE());
        holder.displayType.setText(womenTopModelsList.get(position).getWomenCatType());

        return v;
    }

    static class ViewHolder {
        public ImageView image;
        public TextView color;
        public TextView priceView;
        public TextView displayType;

    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView image;

        public DownloadImageTask(ImageView bmImage) {
            this.image = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            image.setImageBitmap(result);
        }
    }
}
