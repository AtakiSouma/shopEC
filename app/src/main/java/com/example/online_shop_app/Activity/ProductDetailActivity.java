package com.example.online_shop_app.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.online_shop_app.API.Chat.ChatRepository;
import com.example.online_shop_app.API.Chat.ChatServices;
import com.example.online_shop_app.API.Chat.Request.CreateConversationRequest;
import com.example.online_shop_app.API.Chat.Response.CreateConversationResponse;
import com.example.online_shop_app.API.Product.ProductRRepository;
import com.example.online_shop_app.API.Product.ProductService;
import com.example.online_shop_app.API.Product.Response.ProductDetailResponse;
import com.example.online_shop_app.Helper.MyTinyDB;
import com.example.online_shop_app.Helper.TinyDB;
import com.example.online_shop_app.HomeActivity;
import com.example.online_shop_app.Models.Product;
import com.example.online_shop_app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    private ProductService productService;
    ImageView product_Image, send_mess;
    TextView product_original_price, product_discount_price, product_name, product_description, product_sold_out, product_rating;
    ImageView btn_back;
    AppCompatButton btn_add_to_cart;
    private MyTinyDB tinyDB;
    private ProductDetailResponse.Product currentProduct;
    private ChatServices chatServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        tinyDB = new MyTinyDB(this);
        productService = ProductRRepository.callApiProduct(this, true);
        String productId = getIntent().getStringExtra("product_id");
        fetchProductDetails(productId);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFromDetail();
            }
        });
        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToCart();
            }
        });
        send_mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage_gotoConversation();
            }
        });


    }

    private void SendMessage_gotoConversation() {
        chatServices = ChatRepository.callApiForChat(this, true);
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth", Context.MODE_PRIVATE);
        String userId_Buyer = sharedPreferences.getString("userId", "");
        String adminId = "6b69954a-2f45-430d-b04b-fdbec67a1939";
        List<String> members = Arrays.asList(adminId, userId_Buyer);
        CreateConversationRequest request = new CreateConversationRequest(members);
        Call<CreateConversationResponse> call = chatServices.createConversation(request);
        call.enqueue(new Callback<CreateConversationResponse>() {
            @Override
            public void onResponse(Call<CreateConversationResponse> call, Response<CreateConversationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CreateConversationResponse.Data data = response.body().getData();
                    Intent intent = new Intent(ProductDetailActivity.this, ConversationActivity.class);
                    intent.putExtra("conversationId", data.getId());
                    intent.putExtra("groupTitle", data.getGroupTitle());
                    // data of user
                    if (data.getMembers() != null && !data.getMembers().isEmpty()) {
                        CreateConversationResponse.Data.Member firstMember = data.getMembers().get(0);
                        CreateConversationResponse.Data.Member.User user = firstMember.getUser();
                        if (user != null) {
                            intent.putExtra("fullName", user.getFull_name());
                            intent.putExtra("avatar", user.getAvatar());
                            intent.putExtra("email", user.getEmail());
                            intent.putExtra("receiverId", adminId);
                        }
                    }

                    startActivity(intent);

                } else {
                    Log.e("API_CALL", "Failure: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CreateConversationResponse> call, Throwable t) {
                Log.e("API_CALL", "Error: " + t.getMessage());

            }
        });

    }

    private void AddToCart() {
        if (currentProduct != null) {
            // Retrieve the cart
            List<ProductDetailResponse.Product> cart = tinyDB.getCart("cart_key");
            // Check if the product is already in the cart
            boolean productExists = false;
            if (cart != null) {
                for (ProductDetailResponse.Product product : cart) {
                    if (product.getId().equals(currentProduct.getId())) {
                        productExists = true;
                        break;
                    }
                }
            }
            if (productExists) {
                // Product already in cart, show error message
                Toast.makeText(this, "Product has been added to cart", Toast.LENGTH_SHORT).show();
            } else {
                ProductDetailResponse.Product product = new ProductDetailResponse.Product(
                        currentProduct.getId(),
                        currentProduct.getName(),
                        currentProduct.getCategory(),
                        currentProduct.getDiscountPrice(),
                        currentProduct.getOriginalPrice(),
                        currentProduct.getQuantity(),
                        1,
                        currentProduct.getProductImages()
                );
                tinyDB.addProductToCart("cart_key", product);
                Log.d("ProductDetail", "Product: " + product);
                Toast.makeText(this, "Add Product to Cart successfully", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Add Product to  failed", Toast.LENGTH_SHORT).show();

        }
    }

    private void backFromDetail() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


    private void fetchProductDetails(String productId) {
        Call<ProductDetailResponse> call = productService.getProductById(productId);
        call.enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentProduct = response.body().getData();

                    Log.d("ProductDetail", "Product: " + currentProduct);

                    displayProductDetails(currentProduct);

                }
            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {

            }
        });
    }

    private void displayProductDetails(ProductDetailResponse.Product product) {
        if (product != null) {
            if (!product.getProductImages().isEmpty()) {
                Glide.with(this)
                        .load(product.getProductImages().get(0).getImageUrl())
                        .into(product_Image);
            } else {
                Log.d("errorr", "image is null");
            }

            product_discount_price.setText(product.getDiscountPrice() + "VND");
            product_original_price.setText(product.getOriginalPrice() + "VND");
            product_rating.setText(product.getRatings() + "");
            product_name.setText(product.getName());
            product_description.setText(product.getDescription());
            product_sold_out.setText(product.getSoldOut() + "");
        }
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        product_Image = findViewById(R.id.product_image_card1);
        product_discount_price = findViewById(R.id.product_discount_price);
        product_original_price = findViewById(R.id.product_original_price);
        product_rating = findViewById(R.id.product_rating);
        product_name = findViewById(R.id.productName);
        product_description = findViewById(R.id.product_description);
        product_sold_out = findViewById(R.id.product_sold_out);
        btn_back = findViewById(R.id.back_from_detail);
        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);
        send_mess = findViewById(R.id.send_mess);

    }
}