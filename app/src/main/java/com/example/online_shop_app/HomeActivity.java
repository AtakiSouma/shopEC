package com.example.online_shop_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import static com.mapbox.maps.plugin.gestures.GesturesUtils.getGestures;
import static com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils.getLocationComponent;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.online_shop_app.Fragment.BookmarksFragment;
import com.example.online_shop_app.Fragment.CartFragment;
import com.example.online_shop_app.Fragment.HomeFragment;
import com.example.online_shop_app.Fragment.MapFragment;
import com.example.online_shop_app.Fragment.PersonFragment;
import com.example.online_shop_app.databinding.ActivityHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.plugin.gestures.OnMoveListener;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private Fragment homeFragment;
    private Fragment profileFragment;
    private Fragment bookmarksFragment;
    private Fragment cartFragment;
    private Fragment mapFragment;
    private Fragment activeFragment;
    private FragmentManager fragmentManager;
    private MapView mapView;
    FloatingActionButton floatingActionButton;

    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result) {
                Toast.makeText(HomeActivity.this, "Permission granted!", Toast.LENGTH_SHORT).show();
            }
        }
    });
    private final OnIndicatorBearingChangedListener onIndicatorBearingChangedListener = new OnIndicatorBearingChangedListener() {
        @Override
        public void onIndicatorBearingChanged(double v) {
            mapView.getMapboxMap().setCamera(new CameraOptions.Builder().bearing(v).build());
        }
    };
    private final OnIndicatorPositionChangedListener onIndicatorPositionChangedListener = new OnIndicatorPositionChangedListener() {
        @Override
        public void onIndicatorPositionChanged(@NonNull Point point) {
            mapView.getMapboxMap().setCamera(new CameraOptions.Builder().center(point).zoom(20.0).build());
            getGestures(mapView).setFocalPoint(mapView.getMapboxMap().pixelForCoordinate(point));

        }


    };
    private final OnMoveListener onMoveListener = new OnMoveListener() {


        @Override
        public void onMoveEnd(@NonNull MoveGestureDetector moveGestureDetector) {
            getLocationComponent(mapView).removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
            getLocationComponent(mapView).removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
            getGestures(mapView).removeOnMoveListener(onMoveListener);
            floatingActionButton.show();
        }

        @Override
        public void onMoveBegin(@NonNull MoveGestureDetector moveGestureDetector) {

        }

        @Override
        public boolean onMove(@NonNull MoveGestureDetector moveGestureDetector) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        setContentView(binding.getRoot());


        homeFragment = new HomeFragment();
        profileFragment = new PersonFragment();
        bookmarksFragment = new BookmarksFragment();
        cartFragment = new CartFragment();
        mapFragment = new MapFragment();
        fragmentManager = getSupportFragmentManager();
        // Set the initial fragment
        activeFragment = homeFragment;
        fragmentManager.beginTransaction()
                .add(R.id.frame_main_layout, homeFragment, "home")
                .commit();
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_fragment) {
                showFragment(homeFragment);
            } else if (itemId == R.id.profile_fragment) {
                if (fragmentManager.findFragmentByTag("profile") == null) {
                    fragmentManager.beginTransaction().add(R.id.frame_main_layout, profileFragment, "profile").hide(activeFragment).commit();
                }
                showFragment(profileFragment);
            } else if (itemId == R.id.love_fragment) {
                if (fragmentManager.findFragmentByTag("bookmarks") == null) {
                    fragmentManager.beginTransaction().add(R.id.frame_main_layout, bookmarksFragment, "bookmarks").hide(activeFragment).commit();
                }
                showFragment(bookmarksFragment);
            } else if (itemId == R.id.cart_fragment) {
                if (fragmentManager.findFragmentByTag("cart") == null) {
                    fragmentManager.beginTransaction().add(R.id.frame_main_layout, cartFragment, "cart").hide(activeFragment).commit();
                }
                showFragment(cartFragment);
            } else if (itemId == R.id.map_fragment) {
                if (fragmentManager.findFragmentByTag("map") == null) {
                    fragmentManager.beginTransaction().add(R.id.frame_main_layout, mapFragment, "map").hide(activeFragment).commit();
                }
                showFragment(mapFragment);

            }
            return true;
        });
        getFCMToken();
    }

    private void getFCMToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String token = task.getResult();
                Log.i("My RefreshToken", token);
            }
        });
    }

    //    private void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frame_main_layout, fragment);
//        fragmentTransaction.commit();
//
//    }
    private void showFragment(Fragment fragment) {
        if (fragment != activeFragment) {
            fragmentManager.beginTransaction().hide(activeFragment).show(fragment).commit();
            activeFragment = fragment;
        }
    }

}