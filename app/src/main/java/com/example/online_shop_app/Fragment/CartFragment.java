package com.example.online_shop_app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_shop_app.API.Payment.PaymentRepository;
import com.example.online_shop_app.API.Payment.PaymentService;
import com.example.online_shop_app.API.Payment.Request.OrderRequest;
import com.example.online_shop_app.API.Payment.Request.PaymentRequest;
import com.example.online_shop_app.API.Payment.Response.OrderResponse;
import com.example.online_shop_app.API.Payment.Response.PaymentResponse;
import com.example.online_shop_app.API.Product.Response.ProductDetailResponse;
import com.example.online_shop_app.Activity.OrderCompletedActivity;
import com.example.online_shop_app.Adapter.CartAdapter;
import com.example.online_shop_app.Helper.MyTinyDB;
import com.example.online_shop_app.HomeActivity;
import com.example.online_shop_app.R;
import com.example.online_shop_app.Utils.JWTUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {

    AppCompatButton btn_payment;
    private PaymentService paymentService;

    private PaymentRepository paymentRepository;
    private RecyclerView cart_item;
    private MyTinyDB tinyDB;
    private LinearLayout empty_cart;
    private CartAdapter cartAdapter;
    private List<ProductDetailResponse.Product> productList = new ArrayList<>(); // List to hold cart products
    private TextView total_count_carts;
    AppCompatButton btn_continue_shopping;
    private TextView go_to_complete_order;

    public CartFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //*************************************
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        btn_payment = view.findViewById(R.id.payment_btn);
        cart_item = view.findViewById(R.id.cart_item);
        empty_cart = view.findViewById(R.id.empty_cart_layout);
        tinyDB = new MyTinyDB(getContext());
        total_count_carts = view.findViewById(R.id.total_count_carts);
        btn_continue_shopping = view.findViewById(R.id.btn_continue_shopping);
        go_to_complete_order = view.findViewById(R.id.go_to_complete_order);
        // end init View
        //*************************************
        // Initialize RecyclerView and CartAdapter
        cartAdapter = new CartAdapter(productList, tinyDB);
        cartAdapter.setOnCartUpdatedListener(new CartAdapter.OnCartUpdatedListener() {
            @Override
            public void onCartUpdated() {
                updateTotalPrice();

            }
        });
        cart_item.setLayoutManager(new LinearLayoutManager(getContext()));
        cart_item.setAdapter(cartAdapter);
        loadCartItems();

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                makePayment();
                createOrder();
            }
        });
        btn_continue_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToContinueShopping();
            }
        });
        go_to_complete_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCompletedOrdered();
            }
        });
        return view;
    }

    private void gotoCompletedOrdered() {
        Intent intent = new Intent(getContext(), OrderCompletedActivity.class);
        ;
        startActivity(intent);
        getActivity().finish();
    }


    private void backToContinueShopping() {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void loadCartItems() {
        // Get products from TinyDB
        List<ProductDetailResponse.Product> productsInCart = tinyDB.getCart("cart_key");
        // Check if cart is empty
        if (productsInCart == null || productsInCart.isEmpty()) {
            // Cart is empty, show message
            cart_item.setVisibility(View.GONE); // Hide RecyclerView
            empty_cart.setVisibility(View.VISIBLE);
        } else {
            productList.clear();
            productList.addAll(productsInCart);
            cartAdapter.notifyDataSetChanged();

            // Hide empty cart message if previously shown
            empty_cart.setVisibility(View.GONE);
            cart_item.setVisibility(View.VISIBLE); // Show RecyclerView
        }
//        updateTotalCount();
        updateTotalPrice();

    }

    private void updateTotalPrice() {
        double totalPrice = 0;
        for (ProductDetailResponse.Product product : productList) {
            totalPrice += product.getDiscountPrice() * product.getNum_cart();
        }
        total_count_carts.setText(String.format("%.2f VND", totalPrice));
    }

    private void updateTotalCount() {
        int totalCount = 0;
        for (ProductDetailResponse.Product product : productList) {
            totalCount += product.getNum_cart();
        }

        total_count_carts.setText(String.valueOf(totalCount));
    }

    private void makePayment() {
        paymentService = paymentRepository.payment(getContext(), true);

        PaymentRequest paymentRequest = new PaymentRequest("order2");
        Call<PaymentResponse> call = paymentService.makePayment(paymentRequest);
        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PaymentResponse paymentResponse = response.body();
                    if (paymentResponse.isSuccess()) {
                        // Handle success
                        String orderUrl = paymentResponse.getData().getOrderUrl();

                        Log.d("Payment", "Payment successful! Order URL: " + paymentResponse.getData().getOrderUrl());
                        Toast.makeText(getContext(), "Payment Successful!", Toast.LENGTH_SHORT).show();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(orderUrl));
                        startActivity(browserIntent);

                    } else {
                        // Handle failure
                        Log.e("Payment", "Payment failed: " + paymentResponse.getData().getReturnMessage());
                        Toast.makeText(getContext(), "Payment Failed: " + paymentResponse.getData().getReturnMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.e("Payment", "Payment failed: " + response.message());
                    Toast.makeText(getContext(), "Payment Failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                Log.e("Payment", "API call failed", t);
                Toast.makeText(getContext(), "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void createOrder() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("auth", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", "");
        String userId = sharedPreferences.getString("userId", "");
        if (!accessToken.isEmpty()) {
            try {
                JWTUtils.decoded(accessToken);
            } catch (Exception e) {
                Log.e("JWTUtils", "Failed to decode JWT", e);
            }
        } else {
            Log.d("JWTUtils", "No access token found in SharedPreferences");
        }
//        String userId = "2b492f29-226a-43e4-b632-58aaa786b6a9";
        paymentService = paymentRepository.payment(getContext(), true);
        List<OrderRequest.CartItem> cartItems = new ArrayList<>();
        for (ProductDetailResponse.Product product : productList) {
            cartItems.add(new OrderRequest.CartItem(product.getId(), product.getNum_cart()));
        }
        OrderRequest orderRequest = new OrderRequest(userId, cartItems);
        Call<OrderResponse> call = paymentService.createOrder(orderRequest);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OrderResponse orderResponse = response.body();
                    if (orderResponse.isSuccess()) {
                        // Handle success
                        String orderUrl = orderResponse.getData().getPaymentData().getOrderUrl();
                        Log.d("Order", "Order created successfully! Order URL: " + orderUrl);
                        Toast.makeText(getContext(), "Order created successfully!", Toast.LENGTH_SHORT).show();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(orderUrl));
                        startActivity(browserIntent);
                        clearCart();
                    } else {
                        // Handle failure
                        Log.e("Order", "Order creation failed: " + orderResponse.getData().getPaymentData().getReturnMessage());
                        Toast.makeText(getContext(), "Order creation failed: " + orderResponse.getData().getPaymentData().getReturnMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.e("Order", "Order creation failed: " + response.message());
                    Toast.makeText(getContext(), "Order creation failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("Order", "API call failed", t);
                Toast.makeText(getContext(), "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void clearCart() {
        tinyDB.clearCart("cart_key");
        productList.clear();
        cartAdapter.notifyDataSetChanged();
        empty_cart.setVisibility(View.VISIBLE);
        cart_item.setVisibility(View.GONE);
        updateTotalPrice();

    }

}