package com.example.online_shop_app.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.online_shop_app.API.FCMMessage;
import com.example.online_shop_app.API.FCMResponse;
import com.example.online_shop_app.API.FCMService;
import com.example.online_shop_app.API.Order.OrderRepository;
import com.example.online_shop_app.API.Order.OrderServices;
import com.example.online_shop_app.API.Order.Response.OrderUpdateResponse;
import com.example.online_shop_app.Models.Order;
import com.example.online_shop_app.R;
import com.example.online_shop_app.Utils.DateUtils;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderManagerAdapter extends RecyclerView.Adapter<OrderManagerAdapter.OrderManagerViewHolder> {
    private List<Order> orderList;
    private Context context;
    private OrderServices orderServices;
    private FCMService fcmService;
    // Initialize Retrofit for FCMService


    public OrderManagerAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
        Retrofit retrofitFCM = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/") // FCM base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        fcmService = retrofitFCM.create(FCMService.class);

    }

    @NonNull
    @Override
    public OrderManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_order, parent, false);
        return new OrderManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderManagerViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.order_name.setText("# -" + order.getOrderName());
        holder.total_price.setText("$" + order.getTotalPrice() + " VND");
        holder.time_order.setText(DateUtils.formatTimestamp(order.getCreatedAt()));
        holder.phone_user.setText("Phone :" + order.getUser().getPhone_number());
        holder.address_user.setText("Del on " + order.getUser().getAddress());
        holder.name_of_customer.setText(order.getUser().getFull_name());
        // Load the product image using the first OrderItem's product image
        if (order.getOrderItem() != null && !order.getOrderItem().isEmpty()) {
            String imageUrl = order.getOrderItem().get(0).getProduct().getProductImages().get(0).getImage_url();
            Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.images_first_product);
        }
        holder.status_order.setText(order.getStatus());
        // Set background tint and text color based on status
        updateStatusTextView(holder.status_order, order.getStatus());
        holder.btn_update_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(order, holder);
            }
        });
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderManagerViewHolder extends RecyclerView.ViewHolder {
        private TextView order_name, name_of_customer, time_order, phone_user, address_user, total_price, status_order;
        private ImageView images_first_product, btn_update_order;

        public OrderManagerViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the TextViews
            order_name = itemView.findViewById(R.id.order_name);
            name_of_customer = itemView.findViewById(R.id.name_of_customer);
            time_order = itemView.findViewById(R.id.time_order);
            phone_user = itemView.findViewById(R.id.phone_user);
            address_user = itemView.findViewById(R.id.address_user);
            total_price = itemView.findViewById(R.id.total_price);
            status_order = itemView.findViewById(R.id.status_order);

            // Initialize the ImageViews
            images_first_product = itemView.findViewById(R.id.images_first_product);
            btn_update_order = itemView.findViewById(R.id.btn_update_order);
        }
    }

    private void showDialog(Order order, OrderManagerViewHolder holder) {
        //
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_box, null);
        builder.setView(dialogView);
        // get refernce id
        ImageView btnCancelDialog = dialogView.findViewById(R.id.btn_cncel_dialog);
        TextView textTitle = dialogView.findViewById(R.id.text_title);
        TextView subTitle = dialogView.findViewById(R.id.sub_title);
        MaterialButton btnCancelOrder = dialogView.findViewById(R.id.btn_cancel_order);
        MaterialButton btnCompleteOrder = dialogView.findViewById(R.id.btn_complete_order);


        // set title and sub titile
        textTitle.setText("Confirm Order");
        subTitle.setText("update the status of this Order?" + order.getOrderName());


        // Create the AlertDialog
        AlertDialog alertDialog = builder.create();

        // btn cancel button
        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        // cancel order
        btnCompleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrderStatus(order.getId(), "Completed", holder, alertDialog, order.getUser().getFcmToken());
            }
        });
        // complete order
        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrderStatus(order.getId(), "Canceled", holder, alertDialog, order.getUser().getFcmToken());
            }
        });

        // Show the dialog
        alertDialog.show();
    }

    private void updateOrderStatus(String orderId, String status, OrderManagerViewHolder holder, AlertDialog alertDialog, String fcmToken) {
        orderServices = OrderRepository.callApiOrder(context, true);
        Call<OrderUpdateResponse> call;

        if (status.equals("Completed")) {
            call = orderServices.updateCompletedOrder(orderId);
        } else {
            call = orderServices.updateCancelOrder(orderId);

        }
        call.enqueue(new Callback<OrderUpdateResponse>() {
            @Override
            public void onResponse(Call<OrderUpdateResponse> call, Response<OrderUpdateResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Order " + status + " successfully", Toast.LENGTH_SHORT).show();
                    holder.status_order.setText(status);
                    updateStatusTextView(holder.status_order, status);
                    sendFCMNotification(status, fcmToken);

                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "Failed to update order", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<OrderUpdateResponse> call, Throwable t) {
                Toast.makeText(context, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updateStatusTextView(TextView statusTextView, String status) {
        if (status.equals("Completed")) {
            statusTextView.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.light_green));
            statusTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else if (status.equals("Canceled")) {
            statusTextView.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.light_red));
            statusTextView.setTextColor(ContextCompat.getColor(context, R.color.red_1));
        }
    }

    private void sendFCMNotification(String status, String fcmToken) {
        String title = "Order Update";
        String body = "Order " + status + " Successfully";
        FCMMessage.Message.Notification notification = new FCMMessage.Message.Notification(title, body);
        FCMMessage.Message message = new FCMMessage.Message(fcmToken, notification);
        FCMMessage fcmMessage = new FCMMessage(message);
        Call<FCMResponse> call = fcmService.sendMessage(fcmMessage);
        call.enqueue(new Callback<FCMResponse>() {
            @Override
            public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Notification sent successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to send notification", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FCMResponse> call, Throwable t) {
                Toast.makeText(context, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
