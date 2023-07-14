package com.example.karansinh_jadav_final_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private List<Cart> cartList;
    private CartAdapter cartAdapter;


    public CartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.cartRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartList = new ArrayList<>();
        cartAdapter = new CartAdapter(getContext(), cartList);
        recyclerView.setAdapter(cartAdapter);

        db.collection("Cart")
                .document(auth.getCurrentUser().getUid())
                .collection("Cart Items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Cart cart = document.toObject(Cart.class);
                                cartList.add(cart);
                            }
                            cartAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Error getting cart items", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return view;

    }
}
