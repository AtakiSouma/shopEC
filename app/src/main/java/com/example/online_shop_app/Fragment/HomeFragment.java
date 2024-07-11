package com.example.online_shop_app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.online_shop_app.API.Category.CategoryRepository;
import com.example.online_shop_app.API.Category.CategoryService;
import com.example.online_shop_app.API.Category.Response.CategoryResponse;
import com.example.online_shop_app.API.Product.ProductService;
import com.example.online_shop_app.Activity.AllMessagesConvesationActivity;
import com.example.online_shop_app.Activity.detail.SearchActivity;
import com.example.online_shop_app.Adapter.CategoryAdapter;
import com.example.online_shop_app.Adapter.ProductAdapter;
import com.example.online_shop_app.Models.Category;
import com.example.online_shop_app.Models.Product;
import com.example.online_shop_app.R;
import com.example.online_shop_app.ViewModels.HomeViewModelFactory;
import com.example.online_shop_app.ViewModels.HomeViewModels;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView, recyclerViewProduct;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private HomeViewModels homeViewModels;

    private ShimmerFrameLayout shimmerFrameLayout;
    private ShimmerFrameLayout shimmerFrameLayoutCategory;
    private EditText editTextSearch;
    private ImageView gotochat;

    public HomeFragment() {
    }


    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageSlider imageSlider = view.findViewById(R.id.image_slider_banner);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner5, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        //********** end handle banner

        //************ start*  init View
        //----------------------
        recyclerView = view.findViewById(R.id.category_recycle_view);
        recyclerViewProduct = view.findViewById(R.id.product_recycle_view);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_popular_product);
        shimmerFrameLayoutCategory = view.findViewById(R.id.shimmer_popular_category);
        editTextSearch = view.findViewById(R.id.editTextSearch);
        gotochat = view.findViewById(R.id.gotochat);
        //************  end* init View
        //----------------------
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));        //************ Category


        HomeViewModelFactory factory = new HomeViewModelFactory(requireContext(), true);

        homeViewModels = new ViewModelProvider(this, factory).get(HomeViewModels.class);
        gotochat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChat();
            }
        });
        editTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate_search_activity();

            }
        });
        // Start Shimmer animation
        startShimmerFetchProduct();
        startShimmerFetchPopularCategory();


        // Call API
        //***********
        homeViewModels.getCategories(1, 10).observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if (categoryAdapter == null) {
                    stopShimmerFetchPopularCategory();
                    categoryAdapter = new CategoryAdapter(categories);
                    recyclerView.setAdapter(categoryAdapter);
                } else {
                    categoryAdapter.setCategoryList(categories);
                    categoryAdapter.notifyDataSetChanged();
                }
            }
        });


        //************

        // fetch popular  products
        homeViewModels.getProducts(1, 10).observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                // Stop Shimmer animation
                stopShimmerFetchPopularProduct();
                if (productAdapter == null) {
                    productAdapter = new ProductAdapter(getContext(), products);
                    recyclerViewProduct.setAdapter(productAdapter);
                } else {
                    productAdapter.setProductList(products);
                    categoryAdapter.notifyDataSetChanged();
                }
            }
        });
        // end


        return view;

    }

    private void goToChat() {
        Intent intent = new Intent(getContext(), AllMessagesConvesationActivity.class);
        startActivity(intent);

    }

    private void navigate_search_activity() {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        startActivity(intent);

    }

    private void startShimmerFetchProduct() {
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerViewProduct.setVisibility(View.GONE);
    }

    private void stopShimmerFetchPopularProduct() {
        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
        recyclerViewProduct.setVisibility(View.VISIBLE);
    }

    private void startShimmerFetchPopularCategory() {
        shimmerFrameLayoutCategory.startShimmerAnimation();
        shimmerFrameLayoutCategory.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void stopShimmerFetchPopularCategory() {
        shimmerFrameLayoutCategory.stopShimmerAnimation();
        shimmerFrameLayoutCategory.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }


}