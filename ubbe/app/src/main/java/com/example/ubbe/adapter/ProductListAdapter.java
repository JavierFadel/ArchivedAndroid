package com.example.ubbe.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ubbe.MainActivity;
import com.example.ubbe.ProductDetail;
import com.example.ubbe.R;
import com.example.ubbe.model.Product;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.CardViewHolder> {
    private List<Product> productList;
    private Context ctx;

    public ProductListAdapter(Context context, List<Product> productList) {
        this.productList = productList;
        this.ctx = context;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Turn xml file into java object
        CardView cv = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list, viewGroup, false);
        return new CardViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewHolder holder, final int position) {
        // Attach data to view object
        Product product = productList.get(position);

        // Image loader.
        Glide.with(holder.itemView.getContext())
                .load(product.getPhoto())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.productImage);

        holder.tvProduct.setText(product.getItem());
        holder.tvVersion.setText(product.getVersion());
        holder.tvPrice.setText(product.getPrice());

        // Todo: set listener to detail page

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ctx.getApplicationContext(), ProductDetail.class);
//                intent.putExtra("EXTRA_POSITION", position);
//                ctx.startActivity(intent)
                Intent intent = new Intent(ctx, ProductDetail.class);
                intent.putExtra(ProductDetail.EXTRA_POSITION, position);
                ctx.startActivity(intent);
                Log.d("Intent: ", "onClick: " + position);
            }
        });

        // TODO: Fuck this shit, drop this shit, fuck 'em.

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "ProductListAdapter";
        TextView tvProduct, tvVersion, tvPrice;
        ImageView productImage;
        CardView cardView;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProduct = itemView.findViewById(R.id.product_title);
            tvVersion = itemView.findViewById(R.id.product_version);
            tvPrice = itemView.findViewById(R.id.pricing);
            productImage = itemView.findViewById(R.id.img_item_photo);
            cardView = (CardView) itemView;
        }

//        @Override
//        public void onClick(View v) {
//            int position = getAdapterPosition();
//            Product product = productList.get(position);
//
//            Intent intent = new Intent(ctx, ProductDetail.class);
//            intent.putExtra("KEY_ITEM", product.getItem());
//            intent.putExtra("KEY_VERSION", product.getVersion());
//            intent.putExtra("KEY_PRICE", product.getPrice());
//            intent.putExtra("KEY_DESC", product.getDesc());
//            intent.putExtra("KEY_IMAGE", product.getPhoto());
//            ctx.startActivity(intent);
//
//            Log.d(TAG, "onClick: " + product.getItem());
//        }
    }
}
