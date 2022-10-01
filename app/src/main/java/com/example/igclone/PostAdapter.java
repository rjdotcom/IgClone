package com.example.igclone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.igclone.fragments.DetailActivity;
import com.example.igclone.fragments.ProfileFragment;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }



    //Clear all elements of the recycler
    public void clear(){
        posts.clear();
        notifyDataSetChanged();
    }

    //    add a list of items
    public void addAll(List<Post> postList){

        posts.addAll(postList);
        notifyDataSetChanged();

    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvUsername;
        private TextView tvUsername2;
        private TextView  tvCaption;
        private ImageView imagePost;
        ParseUser currentUser;

        ImageView ivProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvUsername2 = itemView.findViewById(R.id.tvusername2);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            imagePost = itemView.findViewById(R.id.imagePost);
            ivProfile = itemView.findViewById(R.id.ivProfile);

        }

        public void bind(Post post) {
//            Bind data into the view Element
            tvUsername.setText(post.getUser().getUsername());
            tvUsername2.setText(post.getUser().getUsername());
            tvCaption.setText(post.getDescription());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(imagePost);
            }
            Glide.with(context).load(post.getUser().getParseFile("profile").getUrl()).into(ivProfile);


            imagePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("post", Parcels.wrap(post));
                    context.startActivity(i);

                }
            });
         ivProfile.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                 ProfileFragment profileFragment = ProfileFragment.newInstance("Some Title");
                 Bundle bundle = new Bundle();
                 bundle.putParcelable("Post", Parcels.wrap(post));
                 profileFragment.setArguments(bundle);

                 fragmentManager.beginTransaction().replace(R.id.flContainer, profileFragment).commit();
             }
         });
        }


    }

}
