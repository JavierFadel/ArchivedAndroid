package com.example.ubbe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ubbe.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductDetail extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_id";

    private TextView productItem, productVersion, productPrice, productDesc;
    private ImageView productImage;

    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        productItem = findViewById(R.id.product_title);
        productVersion = findViewById(R.id.product_version);
        productPrice = findViewById(R.id.product_price);
        productDesc = findViewById(R.id.product_desc);
        productImage = findViewById(R.id.product_image);

        int productID = getIntent().getIntExtra(EXTRA_POSITION, 0);



//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            String item = bundle.getString("KEY_NAME");
//            String version = bundle.getString("KEY_VERSION");
//            String price = bundle.getString("KEY_PRICE");
//            String desc = bundle.getString("KEY_DESC");
//            int image = bundle.getInt("KEY_IMAGE");
//
//            productItem.setText(item);
//            productVersion.setText(version);
//            productPrice.setText(price);
//            productDesc.setText(desc);
//
//            Glide.with(this)
//                    .load(image)
//                    .into(productImage);
//        }

    }
}
