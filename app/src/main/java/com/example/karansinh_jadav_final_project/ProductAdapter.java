package com.example.karansinh_jadav_final_project;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    Context context;
    ArrayList<Pizza> pizzaArrayList;
    public ProductAdapter(Context context, ArrayList<Pizza> pizzaArrayList) {
        this.context = context;
        this.pizzaArrayList = pizzaArrayList;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vobj = LayoutInflater.from(context).inflate(R.layout.pizza_list,parent,false);
        return new ProductViewHolder(vobj);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Pizza p = pizzaArrayList.get(position);
        holder.productheading.setText(p.getProduct_heading());
        holder.productprice.setText(p.getPrice());
        holder.productdescription.setText("Toppings: " + p.getProduct_description1());

        Picasso.get().load(p.getProduct_img()).into(holder.productimg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("PRODUCT_ID", p.getProduct_heading());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pizzaArrayList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView productheading;
        ImageView productimg;
        TextView productprice;
        TextView productdescription;

        public ProductViewHolder(@NotNull View item){
            super(item);
            productheading = item.findViewById(R.id.product_heading);
            productimg = item.findViewById(R.id.product_image);
            productprice = item.findViewById(R.id.product_price);
            productdescription = item.findViewById(R.id.product_description);
        }

    }
}