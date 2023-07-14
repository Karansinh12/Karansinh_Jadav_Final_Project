package com.example.karansinh_jadav_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CartActivity extends AppCompatActivity {

    Button checkout, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CartFragment cartFragment = new CartFragment();
        fragmentTransaction.add(R.id.cart_fragment_container, cartFragment);

        fragmentTransaction.commit();


        checkout = findViewById(R.id.proceed_to_checkout_button);
        back = findViewById(R.id.back_button);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });

    }

}
