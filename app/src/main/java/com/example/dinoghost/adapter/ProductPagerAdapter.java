package com.example.dinoghost.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dinoghost.model.Product;
import com.example.dinoghost.ui.ProductFragment;

import java.util.List;

public class ProductPagerAdapter extends FragmentStateAdapter {
    private List<Product> products;

    public ProductPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Product> products) {
        super(fragmentManager, lifecycle);
        this.products = products;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new ProductFragment(products.get(position));
    }

    @Override
    public int getItemCount() {
        if (products == null) {
            return 0;
        }
        return products.size();
    }
}
