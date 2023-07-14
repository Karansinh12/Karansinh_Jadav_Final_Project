package com.example.karansinh_jadav_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    RecyclerView product_recyclerview;
    ArrayList<Pizza> pizzaArrayList;
    ProductAdapter productAdapter;
    DatabaseReference db;
    ImageView cartImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        cartImage = findViewById(R.id.cart_image);

        product_recyclerview=findViewById(R.id.product_recyclerView);
        db = FirebaseDatabase.getInstance().getReference("Pizzas");
        product_recyclerview.setHasFixedSize(true);
        product_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        pizzaArrayList = new ArrayList<>();

        productAdapter = new ProductAdapter(this,pizzaArrayList);
        product_recyclerview.setAdapter(productAdapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Pizza pizza = dataSnapshot.getValue(Pizza.class);
                    pizzaArrayList.add(pizza);
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }
}
