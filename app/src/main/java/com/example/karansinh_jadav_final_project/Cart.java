package com.example.karansinh_jadav_final_project;

public class Cart {
    private String Product_Name;
    private String Product_Price;
    private String Current_Date;
    private String Current_Time;
    private String Total_Quantity;
    private String Product_Image;

    public Cart() {}

    public Cart(String productName, String productPrice, String currentDate, String currentTime, String totalQuantity, String productImage) {
        this.Product_Name = productName;
        this.Product_Price = productPrice;
        this.Current_Date = currentDate;
        this.Current_Time = currentTime;
        this.Total_Quantity = totalQuantity;
        this.Product_Image = productImage;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String productName) {
        this.Product_Name = productName;
    }

    public String getProduct_Price() {
        return Product_Price;
    }

    public void setProduct_Price(String productPrice) {
        this.Product_Price = productPrice;
    }

    public String getCurrent_Date() {
        return Current_Date;
    }

    public void setCurrent_Date(String currentDate) {
        this.Current_Date = currentDate;
    }

    public String getCurrent_Time() {
        return Current_Time;
    }

    public void setCurrent_Time(String currentTime) {
        this.Current_Time = currentTime;
    }

    public String getTotal_Quantity() {
        return Total_Quantity;
    }

    public void setTotal_Quantity(String totalQuantity) {
        this.Total_Quantity = totalQuantity;
    }

    public String getProduct_Image() {
        return Product_Image;
    }

    public void setProduct_Image(String productImage) {
        this.Product_Image = productImage;
    }
}
