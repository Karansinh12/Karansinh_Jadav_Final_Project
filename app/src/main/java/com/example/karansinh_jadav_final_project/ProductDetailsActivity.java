package com.example.karansinh_jadav_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView product_heading, product_description, product_price, quantity_text_view;
    ImageView product_image;
    Button add_to_cart, plus_button, minus_button, back_menu,view_cart;
    int quantity = 1;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Get the product ID from the intent
        Intent intent = getIntent();
        String productId = intent.getStringExtra("PRODUCT_ID");

        product_heading = findViewById(R.id.product_heading);
        product_description = findViewById(R.id.product_description);
        product_price = findViewById(R.id.product_price);
        product_image = findViewById(R.id.product_image);
        plus_button = findViewById(R.id.plus_button);
        minus_button = findViewById(R.id.minus_button);
        quantity_text_view = findViewById(R.id.quantity_text);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Pizzas");
        db.orderByChild("product_heading").equalTo(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Pizza pizza = dataSnapshot.getValue(Pizza.class);
                        product_heading.setText(pizza.getProduct_heading());
                        product_description.setText(pizza.getProduct_description2());
                        product_price.setText(pizza.getPrice());
                        Picasso.get().load(pizza.getProduct_img()).into(product_image);

                        String priceString = product_price.getText().toString();
                        int price = Integer.parseInt(priceString.replaceAll("[^\\d]", ""));
                        TextView final_price = findViewById(R.id.final_price_text);

                        plus_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                quantity++;
                                quantity_text_view.setText(String.valueOf(quantity));
                                int finalPrice = price * quantity;
                                final_price.setText(String.valueOf(finalPrice));
                            }
                        });

                        minus_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (quantity > 1) {
                                    quantity--;
                                    quantity_text_view.setText(String.valueOf(quantity));
                                    int finalPrice = price * quantity;
                                    final_price.setText(String.valueOf(finalPrice));
                                }
                            }
                        });

                        add_to_cart = findViewById(R.id.add_to_cart_button);
                        back_menu = findViewById(R.id.back_to_menu);
                        view_cart = findViewById(R.id.view_Cart);
                        add_to_cart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addedToCart(product_heading.getText().toString(), product_price.getText().toString(),pizza.getProduct_img());
                            }
                        });

                        back_menu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(ProductDetailsActivity.this,ProductActivity.class));
                            }
                        });

                        view_cart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(ProductDetailsActivity.this,CartActivity.class));
                            }
                        });
                    }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addedToCart(String productName, String productPrice, String productImage) {
        String saveCurrentDate, saveCurrentTime;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        int price = Integer.parseInt(productPrice.replaceAll("[^\\d]", ""));
        int finalPrice = price * quantity;

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("Product_Name", productName);
        cartMap.put("Product_Price", finalPrice + "");
        cartMap.put("Current_Date", saveCurrentDate);
        cartMap.put("Current_Time", saveCurrentTime);
        cartMap.put("Total_Quantity", String.valueOf(quantity));
        cartMap.put("Product_Image",productImage);


        db.collection("Cart").document(auth.getCurrentUser().getUid())
                .collection("Cart Items").document(productName)
                .set(cartMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProductDetailsActivity.this, "Product added to cart successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProductDetailsActivity.this, "Error adding product to cart: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
