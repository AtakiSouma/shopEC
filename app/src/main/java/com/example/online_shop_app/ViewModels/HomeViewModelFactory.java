package com.example.online_shop_app.ViewModels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HomeViewModelFactory  implements ViewModelProvider.Factory{
    private Context context;
    private boolean useLocal;

    public HomeViewModelFactory(Context context, boolean useLocal) {
        this.context = context;
        this.useLocal = useLocal;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModels.class)) {
            return (T) new HomeViewModels(context, useLocal);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
