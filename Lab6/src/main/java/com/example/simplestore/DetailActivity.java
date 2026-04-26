package com.example.simplestore;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView image = findViewById(R.id.detailImage);
        TextView title = findViewById(R.id.detailTitle);
        TextView price = findViewById(R.id.detailPrice);
        TextView desc = findViewById(R.id.detailDescription);

        Product product = (Product) getIntent().getSerializableExtra("product_data");

        if (product != null) {
            title.setText(product.getTitle());
            price.setText(product.getPrice() + " $");
            desc.setText(product.getDescription());
            Glide.with(this).load(product.getImage()).into(image);
        }
    }
}