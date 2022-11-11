package com.example.dinoghost.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.dinoghost.R;
import com.example.dinoghost.databinding.FragmentProductBinding;
import com.example.dinoghost.model.Product;
import com.example.dinoghost.viewmodel.ProductViewModel;

public class ProductFragment extends Fragment {
    private FragmentProductBinding binding;
    private Product product;

    public ProductFragment(Product product) {
        this.product = product;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProductViewModel viewModel = new ProductViewModel(product);
        binding.setProductViewModel(viewModel);
        Glide.with(this)
                .load(product.getImage())
                .centerCrop()
                .placeholder(R.drawable.logo)
                .into(binding.productImage);
    }
}
