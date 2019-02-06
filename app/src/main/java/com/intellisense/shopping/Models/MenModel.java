package com.intellisense.shopping.Models;

/**
 * Created by Habaiba Abbasi on 19/02/2017.
 */

public class MenModel {

    private String pro_name;

    private String color;

    private String P_ID;

    private String P_PRICE;

    private String Stock;

    private String I_Url;

    private String size;
    private  String combolopia;
    public String getcombolopia ()
    {
        return combolopia;
    }
    public void setcombolopia (String combolopia)
    {
        this.combolopia = combolopia;
    }
    public MenModel(){}
  /*  MenModel (String pro_name,String color,String P_ID,String P_PRICE,String Stock, String I_Url, String size){

        this.pro_name= pro_name;
        this.color=color;
        this.P_ID=P_ID;
        this.I_Url=I_Url;
        this.size=size;
        this.Stock=Stock;
        this.P_PRICE=P_PRICE;


    }
*/
    public String getPro_name ()
    {
        return pro_name;
    }

    public void setPro_name (String pro_name)
    {
        this.pro_name = pro_name;
    }

    public String getColor ()
    {
        return color;
    }

    public void setColor (String color)
    {
        this.color = color;
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

    public String getSize ()
    {
        return size;
    }


    public void setSize (String size)
    {
        this.size = size;
    }


}
