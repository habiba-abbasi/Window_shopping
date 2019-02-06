package com.intellisense.shopping.Models;

import java.util.List;

/**
 * Created by Habaiba Abbasi on 28/02/2017.
 */

public class MenDetailedModel {


    private String pro_name;

    private List<Color> colorlist;

    private String P_ID;

    private String P_PRICE;

    private String Stock;
    private  String combolopia;
    public String getcombolopia ()
    {
        return combolopia;
    }
    public void setcombolopia (String combolopia)
    {
        this.combolopia = combolopia;
    }
    public float getRb() {
        return rb;
    }

    public void setRb(float rb) {
        this.rb = rb;
    }

    private float rb;
    private String I_Url;
    private List<Size> sizelist;
    private List<Images> Imageslist;
    public String getPro_name ()
    {
        return pro_name;
    }

    public void setPro_name (String pro_name)
    {
        this.pro_name = pro_name;
    }

    public List<Color> getColorlist ()
    {
        return colorlist;
    }
    public List<Size> getSizelist ()
    {
        return sizelist;
    }

    public void setColorlist (List<Color> colorl)
    {
        this.colorlist = colorl;
    }
    public void setSizelist (List<Size> sizel)
    {
        this.sizelist = sizel;
    }
    public List<Images> getImageslist ()
    {
        return Imageslist;
    }

    public void setImageslist (List<Images> Imagesl)
    {
        this.Imageslist = Imagesl;
    }
    public String getP_ID ()
    {
        return P_ID;
    }

    public void setP_ID (String P_ID)
    {
        this.P_ID = P_ID;
    }

    public String getP_PRICE ()
    {
        return P_PRICE;
    }

    public void setP_PRICE (String P_PRICE)
    {
        this.P_PRICE = P_PRICE;
    }

    public String getStock()
    {
        return Stock;
    }

    public void setStock (String Stock)
    {
        this.Stock = Stock;
    }

    public String getI_Url ()
    {
        return I_Url;
    }

    public void setI_Url (String I_Url)
    {
        this.I_Url = I_Url;
    }




    public static class Color
    {
        String name;
        public  String getName()
        {
            return  name;
        }
        public void setName(String n)
        {
            name=n;
        }
    }

    public static class Size
    {
        String name;
        public  String getSize()
        {
            return  name;
        }
        public void setSize(String n)
        {
            name=n;
        }
    }
    public static class Images
    {
        String name;
        public  String getImages()
        {
            return  name;
        }
        public void setImages(String n)
        {
            name=n;
        }
    }
}
