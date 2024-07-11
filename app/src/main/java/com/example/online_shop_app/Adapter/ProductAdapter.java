package com.example.online_shop_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.online_shop_app.Activity.ProductDetailActivity;
import com.example.online_shop_app.Models.Product;
import com.example.online_shop_app.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(Context context ,List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.product_title.setText(product.getName());
        holder.product_discount_price.setText(product.getDiscountPrice()+"");
        holder.product_original_price.setText(product.getOriginalPrice()+"");
        if (!product.getImages().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(product.getImages().get(0).getImageUrl()) // Load first image for simplicity
//                    .placeholder(R.drawable.placeholder_image) // Placeholder image
                    .into(holder.imageView);
        }
        // intent to product id
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("product_id", product.getId());
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

  public   static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView product_title;
        TextView product_discount_price;
        TextView product_original_price;
        ImageView imageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_title = itemView.findViewById(R.id.product_title);
            product_discount_price = itemView.findViewById(R.id.product_discountPrice);
            product_original_price = itemView.findViewById(R.id.product_originalPrice);
            imageView = itemView.findViewById(R.id.product_image_card);
        }
    }
}
