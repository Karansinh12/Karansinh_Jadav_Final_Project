package com.example.karansinh_jadav_final_project;

public class Pizza {
    private String product_img;
    private String product_heading;
    private String price;
    private String product_description1;
    private String product_description2;

    public Pizza() {
    }

    public Pizza(String product_img, String product_heading, String price, String product_description1,String product_description2) {
        this.product_img = product_img;
        this.product_heading = product_heading;
        this.price = price;
        this.product_description1 = product_description1;
        this.product_description2 = product_description2;

    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getProduct_heading() {
        return product_heading;
    }

    public void setProduct_heading(String product_heading) {
        this.product_heading = product_heading;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_description1() {
        return product_description1;
    }
    public String getProduct_description2() {
        return product_description2;
    }

    public void setProduct_description1(String product_description1) {
        this.product_description1 = product_description1;
    }
    public void setProduct_description2(String product_description2) {
        this.product_description2 = product_description2;
    }
}
