package com.example.online_shop_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.online_shop_app.Models.Order;
import com.example.online_shop_app.R;
import com.example.online_shop_app.Utils.DateUtils;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_completed_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderName.setText("Order - # " + order.getOrderName());
        holder.orderTotalPrice.setText("Total Price: " + order.getTotalPrice() + " VND");
        holder.productTime.setText(DateUtils.formatTimestamp(order.getCreatedAt()));
        holder.orderDeliveryTime.setText("Delivery on " + order.getStatus());

        // Load the product image using the first OrderItem's product image
        if (order.getOrderItem() != null && !order.getOrderItem().isEmpty()) {
            String imageUrl = order.getOrderItem().get(0).getProduct().getProductImages().get(0).getImage_url();
            Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.productImageCart);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderName, orderTotalPrice, productTime, orderDeliveryTime;
        ImageView productImageCart;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderName = itemView.findViewById(R.id.order_name);
            orderTotalPrice = itemView.findViewById(R.id.order_total_price);
            productTime = itemView.findViewById(R.id.product_time);
            orderDeliveryTime = itemView.findViewById(R.id.order_delivery_time);
            productImageCart = itemView.findViewById(R.id.product_images_cart);
        }
    }

}
