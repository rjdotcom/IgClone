package com.example.igclone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.igclone.LoginActivity;
import com.example.igclone.Post;
import com.example.igclone.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class ProfileFragment extends Fragment {
    Button btnLogout;
    GridView gridView;
    GridAdapter adapter;
    ArrayList<Post> list;
    ImageView ivProfile;
    TextView username;
    ParseUser currentUser;

    public static ProfileFragment newInstance(String title) {
        ProfileFragment frag = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogout= view.findViewById(R.id.btnLogout);
        gridView = view.findViewById(R.id.gridView);
        ivProfile = view.findViewById(R.id.ivProfile);
        username = view.findViewById(R.id.tvUname);
        Bundle bundle = getArguments();
        if (bundle==null){
            currentUser = ParseUser.getCurrentUser(); // this will now be null
        }else{
            Post post = Parcels.unwrap(bundle.getParcelable("Post"));
            currentUser = post.getUser(); // get user clicked on
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentUser.logOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);

            }
        });

       list = new ArrayList<>();
       adapter = new GridAdapter(getContext(),list);
       gridView.setAdapter(adapter);

       username.setText(currentUser.getUsername());

       Glide.with(getContext()).load(currentUser.getParseFile("profile").getUrl()).into(ivProfile);

       queryPost();
    }


    protected void queryPost() {
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATEDAT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){

                    return;
                }

                list.addAll(posts);
                adapter.notifyDataSetChanged();

            }
        });

    }
}
