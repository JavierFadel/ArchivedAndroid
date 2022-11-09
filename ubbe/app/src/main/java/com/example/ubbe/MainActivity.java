package com.example.ubbe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ubbe.adapter.ProductListAdapter;
import com.example.ubbe.data.ProductData;
import com.example.ubbe.model.Product;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvProduct;
    private ArrayList<Product> list = new ArrayList<>();

    // Todo: make image fit to container

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvProduct = findViewById(R.id.rvProduct);
        rvProduct.setHasFixedSize(true);

        list.addAll(ProductData.getListData());
        showRecycleCardView();
    }

    private void showRecycleCardView() {
        rvProduct.setLayoutManager(new LinearLayoutManager(this));
        ProductListAdapter productListAdapter = new ProductListAdapter(MainActivity.this, list);
        rvProduct.setAdapter(productListAdapter);

//        productListAdapter.setListener(new ProductListAdapter.Listener() {
//            @Override
//            public void onClick(int position) {
//                Intent intent = new Intent(MainActivity.this, ProductDetail.class);
//                intent.putExtra(ProductDetail.EXTRA_PRODUCT_ID, position);
//                startActivity(intent);
//                Log.d("position", "onClick: " + position);
//            }
//        });
    }

}
