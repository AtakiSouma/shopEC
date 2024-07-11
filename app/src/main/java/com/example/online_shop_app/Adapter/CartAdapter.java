package com.example.online_shop_app.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.online_shop_app.API.Product.Response.ProductDetailResponse;
import com.example.online_shop_app.Helper.MyTinyDB;
import com.example.online_shop_app.R;

import org.w3c.dom.Text;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<ProductDetailResponse.Product> cartProduct;
    private MyTinyDB tinyDB;
    private OnCartUpdatedListener onCartUpdatedListener;

    public CartAdapter(List<ProductDetailResponse.Product> cartProduct, MyTinyDB tinyDB) {
        this.cartProduct = cartProduct;
        this.tinyDB = tinyDB;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_items, parent, false);
        return new CartViewHolder(view);
    }

    public void setOnCartUpdatedListener(OnCartUpdatedListener onCartUpdatedListener) {
        this.onCartUpdatedListener = onCartUpdatedListener;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductDetailResponse.Product product = cartProduct.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.valueOf(product.getDiscountPrice()) + "VND");
        holder.produdtCategory.setText(product.getCategory().getName());
        if (!product.getProductImages().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(product.getProductImages().get(0).getImageUrl()) // Load first image for simplicity
//                    .placeholder(R.drawable.placeholder_image) // Placeholder image
                    .into(holder.productImage);
        }
        holder.number_in_cart.setText(product.getNum_cart() + "");
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setNum_cart(product.getNum_cart() + 1);
                tinyDB.putCart("cart_key", cartProduct);
                notifyDataSetChanged();
                if (onCartUpdatedListener != null) {
                    onCartUpdatedListener.onCartUpdated();
                }

            }
        });
        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getNum_cart() > 1) {
                    product.setNum_cart(product.getNum_cart() - 1);
                } else {
                    // Remove product if quantity is 1
                    cartProduct.remove(position);
                    Toast.makeText(holder.itemView.getContext(), "Product removed from cart", Toast.LENGTH_SHORT).show();
                }
                tinyDB.putCart("cart_key", cartProduct);
                notifyDataSetChanged();
                if (onCartUpdatedListener != null) {
                    onCartUpdatedListener.onCartUpdated();
                }
            }
        });
        holder.delete_one_product_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartProduct.remove(position);
                tinyDB.putCart("cart_key", cartProduct);
                notifyDataSetChanged();
                Toast.makeText(holder.itemView.getContext(), "Product removed from cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartProduct.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, produdtCategory;
        ImageView productImage;
        ImageView addBtn, minusBtn, delete_one_product_cart;
        TextView number_in_cart;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name_cart);
            productPrice = itemView.findViewById(R.id.product_price_cart);
            productImage = itemView.findViewById(R.id.product_images_cart);
            produdtCategory = itemView.findViewById(R.id.product_categorry);
            addBtn = itemView.findViewById(R.id.btn_add_product);
            minusBtn = itemView.findViewById(R.id.btn_minus_product);
            number_in_cart = itemView.findViewById(R.id.number_in_cart);
            delete_one_product_cart = itemView.findViewById(R.id.btn_delete_from_cart);


        }
    }

    public interface OnCartUpdatedListener {
        void onCartUpdated();
    }
}
