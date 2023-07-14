package com.example.karansinh_jadav_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    private Button back_Button;
    private Button logout_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        TextView messageTextView = findViewById(R.id.messageTextView);
        back_Button = findViewById(R.id.back_Button);
        logout_Button = findViewById(R.id.logout_Button);

        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });

        logout_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}


