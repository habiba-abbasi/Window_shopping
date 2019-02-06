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
import android.widget.RatingBar;
import android.widget.TextView;

import com.intellisense.shopping.Models.WomenDetailedModel;
import com.intellisense.shopping.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Habaiba Abbasi on 03/03/2017.
 */

public class WomenClothDetailedAdapter extends ArrayAdapter<WomenDetailedModel> {

    ArrayList<WomenDetailedModel> womenTopModelsList;
    int resources;
    LayoutInflater inflater;
   ViewHolder holder;
    //imageAdapter imga;
    public WomenClothDetailedAdapter(Context context, int resource, ArrayList<WomenDetailedModel> objects) {
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
            holder.imagemain=(ImageView) v.findViewById(R.id.mainimage);
            holder.image1=(ImageView) v.findViewById(R.id.image1);
            holder.image2=(ImageView) v.findViewById(R.id.image2);
            holder.color =(TextView) v.findViewById(R.id.textcolor);
            holder.proname= (TextView) v.findViewById(R.id.textproname);
            holder.size=(TextView) v.findViewById(R.id.textsize);
            holder.priceView=(TextView) v.findViewById(R.id.textprice);
            holder.rb=(RatingBar) v.findViewById(R.id.ratingBar);
            holder.Type=(TextView)v.findViewById(R.id.womenMenu_Exp);

            // holder.gv=(GridView) v.findViewById(R.id.Habibi);
         /*   imga=new imageAdapter(convertView, R.layout.activity_top_list1, );*/
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }


        // Then later, when you want to display image
        // ImageLoader.getInstance().displayImage(menModelsList.get(position).getI_Url(), holder.image); // Default options will be used
       /* holder.image.setImageResource(R.drawable.ic_launcher);
        new DownloadImageTask(holder.image).execute(menModelsList.get(position).getI_Url());*/
        holder.proname.setText(womenTopModelsList.get(position).getPro_name ());
        holder.priceView.setText("Rs."+womenTopModelsList.get(position).getP_PRICE());
        holder.rb.setRating(womenTopModelsList.get(position).getRb());
        holder.Type.setText(womenTopModelsList.get(position).getWomenCatType());
        StringBuffer sb = new StringBuffer();
        for(WomenDetailedModel.Color c: womenTopModelsList.get(position).getColorlist())
        {
            sb.append(c.getName()+",");
        }
        holder.color.setText(sb);
        StringBuffer sb1 = new StringBuffer();
        for(WomenDetailedModel.Size s: womenTopModelsList.get(position).getSizelist())
        {
            sb1.append(s.getSize()+",");
        }
        holder.size.setText(sb1);

        //List<MenDetailedModel.Images> Imagesl = new ArrayList<MenDetailedModel.Images>();
        int l=0;
        for(WomenDetailedModel.Images imgq: womenTopModelsList.get(position).getImageslist())
        {
            if(l==0)
            {
                holder.imagemain.setImageResource(R.drawable.ic_launcher);
                String ur=imgq.getImages();
                ur=ur.replaceAll("\\/","/");
                String Actual_hehe="http://icoderslab.com/Api/ShoppingApp/";
                Actual_hehe+=ur;
                new DownloadImageTask(holder.imagemain).execute(Actual_hehe);
            }
            else if(l==1)
            {

                holder.image1.setImageResource(R.drawable.ic_launcher);
                String ur=imgq.getImages();
                ur=ur.replaceAll("\\/","/");
                String Actual_hehe="http://icoderslab.com/Api/ShoppingApp/";
                Actual_hehe+=ur;
                new DownloadImageTask(holder.image1).execute(Actual_hehe);
            }
            else if(l==2)
            {
                holder.image2.setImageResource(R.drawable.ic_launcher);
                // holder.image1.setImageResource(R.drawable.ic_launcher);
                String ur=imgq.getImages();
                ur=ur.replaceAll("\\/","/");
                String Actual_hehe="http://icoderslab.com/Api/ShoppingApp/";
                Actual_hehe+=ur;
                new DownloadImageTask(holder.image2).execute(Actual_hehe);
            }
            l++;
        }


        return v;
    }

    static class ViewHolder {
        public ImageView imagemain;
        public ImageView image1;
        public ImageView image2;
        public TextView color;
        public TextView priceView;
        public TextView proname;
        public TextView size;
        public RatingBar rb;
        public TextView Type;

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
