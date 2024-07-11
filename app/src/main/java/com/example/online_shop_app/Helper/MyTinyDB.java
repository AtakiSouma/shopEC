package com.example.online_shop_app.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.online_shop_app.API.Product.Response.ProductDetailResponse;
import com.example.online_shop_app.API.Product.Response.ProductDetailResponse.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyTinyDB {

    private Context context;
    private SharedPreferences preferences;

    public MyTinyDB(Context appContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        context = appContext;
    }

    public void putProduct(String key, ProductDetailResponse.Product product) {
        checkForNullKey(key);
        Gson gson = new Gson();
        putString(key, gson.toJson(product));
    }

    public Product getProduct(String key) {
        String json = getString(key);
        return new Gson().fromJson(json, ProductDetailResponse.Product.class);
    }

    public void putCart(String key, List<ProductDetailResponse.Product> cart) {
        checkForNullKey(key);
        Gson gson = new Gson();
        String json = gson.toJson(cart);
        putString(key, json);
    }

    public List<ProductDetailResponse.Product> getCart(String key) {
        String json = getString(key);
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductDetailResponse.Product>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void addProductToCart(String key, ProductDetailResponse.Product product) {
        List<ProductDetailResponse.Product> cart = getCart(key);
        if (cart == null) {
            cart = new ArrayList<>();
        }
        cart.add(product);
        putCart(key, cart);
    }

    public void removeProductFromCart(String key, ProductDetailResponse.Product product) {
        List<Product> cart = getCart(key);
        if (cart != null) {
            cart.remove(product);
            putCart(key, cart);
        }
    }

    public void clearCart(String key) {
        remove(key);
    }

    private void putString(String key, String value) {
        checkForNullKey(key);
        checkForNullValue(value);
        preferences.edit().putString(key, value).apply();
    }

    private String getString(String key) {
        return preferences.getString(key, "");
    }

    private void remove(String key) {
        preferences.edit().remove(key).apply();
    }

    private void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

    private void checkForNullValue(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
    }
//    public void clearCart(String key) {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove(key);
//        editor.apply();
//    }
}
