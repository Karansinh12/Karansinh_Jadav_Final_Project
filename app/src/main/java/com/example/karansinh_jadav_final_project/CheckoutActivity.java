package com.example.karansinh_jadav_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CheckoutActivity extends AppCompatActivity {


    private EditText nameEditText, emailEditText, phoneEditText, addressEditText, cardEditText, ExpiryEditText, CVVEditText;
    Button confirmButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        nameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phone);
        addressEditText = findViewById(R.id.address);
        confirmButton = findViewById(R.id.confirm);
        cardEditText = findViewById(R.id.cardnumber);
        ExpiryEditText = findViewById(R.id.expirydate);
        CVVEditText = findViewById(R.id.cvv);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();
                String cardNumber = cardEditText.getText().toString().trim();
                String expiryDate = ExpiryEditText.getText().toString().trim();
                String cvv = CVVEditText.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    nameEditText.setError("Please enter your name");
                    return;
                }

                if (!isValidEmail(email)) {
                    emailEditText.setError("Please enter a valid email address");
                    return;
                }

                if (!isValidName(name)) {
                    nameEditText.setError("Please enter a valid name");
                    return;
                }

                if (!isValidPhoneNumber(phone)) {
                    phoneEditText.setError("Please enter a valid phone number");
                    return;
                }

                if (TextUtils.isEmpty(address)) {
                    addressEditText.setError("Please enter your address");
                    return;
                }

                if (!isValidCardNumber(cardNumber)) {
                    cardEditText.setError("Please enter a valid credit card number");
                    return;
                }

                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ordersRef = database.child("orders");
                DatabaseReference newOrderRef = ordersRef.push();
                newOrderRef.child("name").setValue(name);
                newOrderRef.child("email").setValue(email);
                newOrderRef.child("phone").setValue(phone);
                newOrderRef.child("address").setValue(address);

                Toast.makeText(CheckoutActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CheckoutActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

        cancelButton = findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });


    }

    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidName(String name) {
        String pattern = "^[\\p{L} .'-]+$";
        return name.matches(pattern);
    }

    private boolean isValidPhoneNumber(String phone) {
        String pattern = "^[+]?[0-9]{10,13}$";
        return phone.matches(pattern);
    }

    private boolean isValidCardNumber(String cardNumber) {
        if (TextUtils.isEmpty(cardNumber)) {
            return false;
        }

        cardNumber = cardNumber.replaceAll("\\s+", "");

        if (cardNumber.length() != 16) {
            return false;
        }
        return true;
    }
}



