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

import com.intellisense.shopping.Models.MenModel;
import com.intellisense.shopping.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Habaiba Abbasi on 28/02/2017.
 */

public class MenClothAdapter extends ArrayAdapter<MenModel> {


    ArrayList<MenModel> menModelsList;
    int resources;
    LayoutInflater inflater;
    ViewHolder holder;

    public MenClothAdapter(Context context, int resource, ArrayList<MenModel> objects) {
        super(context, resource, objects);
        menModelsList =objects;
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
            holder.Cambolopia=(TextView) v.findViewById(R.id.manmenuType);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }


        // Then later, when you want to display image
        // ImageLoader.getInstance().displayImage(menModelsList.get(position).getI_Url(), holder.image); // Default options will be used
        //  holder.image.setImageResource(R.drawable.ic_launcher);
        new DownloadImageTask(holder.image).execute(menModelsList.get(position).getI_Url());
        holder.color.setText(menModelsList.get(position).getPro_name ());
        holder.priceView.setText("Rs."+ menModelsList.get(position).getP_PRICE());
        holder.Cambolopia.setText(""+ menModelsList.get(position).getcombolopia());

        return v;
    }

    static class ViewHolder {
        public ImageView image;
        public TextView color;
        public TextView priceView;
        public TextView Cambolopia;

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
