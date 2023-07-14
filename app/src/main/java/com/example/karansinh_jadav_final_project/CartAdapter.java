package com.example.karansinh_jadav_final_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    List<Cart> cartList;

    public CartAdapter(Context context, List<Cart> cartList){
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vobj =  LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list, parent, false);
        return new ViewHolder(vobj);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {

        Cart cart = cartList.get(position);

        holder.product_name.setText(cart.getProduct_Name());
        holder.product_price.setText(cart.getProduct_Price());
        holder.date.setText(cart.getCurrent_Date());
        holder.time.setText(cart.getCurrent_Time());
        holder.quantity.setText(cart.getTotal_Quantity());
        holder.price.setText(String.valueOf(cart.getProduct_Price()));
        Picasso.get().load(cart.getProduct_Image()).into(holder.product_image);

        holder.edit_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    Cart cart = cartList.get(pos);
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra("PRODUCT_ID", cart.getProduct_Name());
                    context.startActivity(intent);
                }
            }
        });

        holder.delete_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    // Get the reference to the document to be deleted
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String product_name = cartList.get(pos).getProduct_Name();
                    FirebaseFirestore.getInstance()
                            .collection("Cart")
                            .document(uid)
                            .collection("Cart Items")
                            .document(product_name)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Remove the item from the list and update the adapter
                                    cartList.remove(pos);
                                    notifyDataSetChanged();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Failed to delete item from cart", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView product_name,product_price,date,time,quantity,price;
        ImageView product_image;
        Button edit_cart,delete_cart;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.product_price);
            product_image = itemView.findViewById(R.id.product_image);
            edit_cart = itemView.findViewById(R.id.edit_cart);
            delete_cart = itemView.findViewById(R.id.delete_cart);
        }
    }
}
