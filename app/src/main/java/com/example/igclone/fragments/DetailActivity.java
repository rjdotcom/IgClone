package com.example.igclone.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.igclone.Post;
import com.example.igclone.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {
    private TextView tvUsername;
    private TextView tvUsername2;
    private TextView  tvCaption;
    private ImageView imagePost;
    Context context;
    ImageView ivProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvUsername = findViewById(R.id.tvUsername);
        tvUsername2 = findViewById(R.id.tvusername2);
        tvCaption = findViewById(R.id.tvCaption);
        imagePost = findViewById(R.id.imagePost);
        ivProfile = findViewById(R.id.ivProfile);
        Post post  = Parcels.unwrap(getIntent().getParcelableExtra("post"));
        //            Bind data into the view Element
        tvUsername.setText(post.getUser().getUsername());
        tvUsername2.setText(post.getUser().getUsername());
        tvCaption.setText(post.getDescription());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(DetailActivity.this).load(post.getImage().getUrl()).into(imagePost);
        }
        Glide.with(DetailActivity.this).load(post.getUser().getParseFile("profile").getUrl()).into(ivProfile);
    }
}