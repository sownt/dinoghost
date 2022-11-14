package com.example.dinoghost.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dinoghost.databinding.ActivityShippingBinding;
import com.example.dinoghost.viewmodel.ShippingViewModel;

public class ShippingActivity extends AppCompatActivity {
    private ActivityShippingBinding binding;
    private ShippingViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShippingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        viewModel = new ShippingViewModel();

        binding.setShipping(viewModel);

        binding.dismiss.setOnClickListener(v -> super.onBackPressed());

        binding.shippingContinue.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(ShippingActivity.this, CheckoutActivity.class);
            bundle.putString("fullName", viewModel.getFullName());
            bundle.putString("email", viewModel.getEmail());
            bundle.putString("phone", viewModel.getPhone());
            bundle.putString("address", viewModel.getAddress());
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}
