package com.tpf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
ImageView img;
TextView desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        desc = findViewById(R.id.desc);
        img = findViewById(R.id.img);

        Picasso.get().load(getIntent().getStringExtra("image")).into(img);
        desc.setText(getIntent().getStringExtra("description"));
    }
}