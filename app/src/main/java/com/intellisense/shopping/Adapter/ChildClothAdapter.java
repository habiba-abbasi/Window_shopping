
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

import com.intellisense.shopping.Models.ChildModel;
import com.intellisense.shopping.Models.ChildModel;
import com.intellisense.shopping.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Habiba Abbasii on 14/03/2017.
 */

public class ChildClothAdapter extends ArrayAdapter<ChildModel> {


    ArrayList<ChildModel> childModelList;
    int resources;
    LayoutInflater inflater;
    ViewHolder holder;

    public ChildClothAdapter(Context context, int resource, ArrayList<ChildModel> objects) {
        super(context, resource, objects);
        childModelList=objects;
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
            holder.displayType=(TextView) v.findViewById(R.id.manmenuType);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }


        new DownloadImageTask(holder.image).execute(childModelList.get(position).getI_Url());
        holder.color.setText(childModelList.get(position).getPro_name ());
        holder.priceView.setText("Rs."+childModelList.get(position).getP_PRICE());
        holder.displayType.setText(childModelList.get(position).getCatType());

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