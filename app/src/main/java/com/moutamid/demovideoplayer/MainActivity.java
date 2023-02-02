package com.moutamid.demovideoplayer;

import static android.view.LayoutInflater.from;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.jiajunhui.xapp.medialoader.MediaLoader;
import com.jiajunhui.xapp.medialoader.bean.VideoItem;
import com.jiajunhui.xapp.medialoader.bean.VideoResult;
import com.jiajunhui.xapp.medialoader.callback.OnVideoLoaderCallBack;
import com.moutamid.demovideoplayer.databinding.ActivityMainBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        Constants.checkApp(this);

        Sprite doubleBounce = new Wave();
        b.spinKit.setIndeterminateDrawable(doubleBounce);

        MediaLoader.getLoader().loadVideos(this, new OnVideoLoaderCallBack() {
            @Override
            public void onResult(VideoResult result) {
//                new Handler().postDelayed(() -> {
                tasksArrayList = result.getItems();

                initRecyclerView();

//                }, 2000);
            }
        });
    }

    private List<VideoItem> tasksArrayList = new ArrayList<>();

    private RecyclerView conversationRecyclerView;
    private RecyclerViewAdapterMessages adapter;

    private void initRecyclerView() {

        conversationRecyclerView = b.videosRv;
//        conversationRecyclerView.addItemDecoration(new DividerItemDecoration(conversationRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new RecyclerViewAdapterMessages();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setReverseLayout(true);
//        conversationRecyclerView.setLayoutManager(linearLayoutManager);
        conversationRecyclerView.setHasFixedSize(true);
        conversationRecyclerView.setNestedScrollingEnabled(false);

        conversationRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        conversationRecyclerView.setAdapter(adapter);

        b.spinKit.setVisibility(View.GONE);
    }

    private class RecyclerViewAdapterMessages extends
//            PaginatedAdapter<VideoItem, RecyclerViewAdapterMessages.ViewHolderRightMessage> {
            Adapter<RecyclerViewAdapterMessages.ViewHolderRightMessage> {

        @NonNull
        @Override
        public ViewHolderRightMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
            return new ViewHolderRightMessage(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolderRightMessage holder, int position) {
            VideoItem videoItem = tasksArrayList.get(holder.getAdapterPosition());

            holder.name.setText(videoItem.getDisplayName());
            holder.size.setText(Constants.getHumanSize(videoItem.getSize()));
            holder.duration.setText(Constants.getHumanTime(videoItem.getDuration()));

//            Constants.getHumanDate(videoItem.getModified())

            Glide.with(getApplicationContext())
                    .load(Uri.fromFile(new File(videoItem.getPath())))
                    .into(holder.imageView);

            holder.parent.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, VideoPlayerActivity.class)
                        .putExtra("path", videoItem.getPath()));
            });

        }

        @Override
        public int getItemCount() {
            if (tasksArrayList == null)
                return 0;
            return tasksArrayList.size();
        }

        public class ViewHolderRightMessage extends ViewHolder {

            TextView name, size, duration;
            ImageView imageView;
            RelativeLayout parent;


            public ViewHolderRightMessage(@NonNull View v) {
                super(v);
                name = v.findViewById(R.id.nameTv);
                size = v.findViewById(R.id.sizeTv);
                duration = v.findViewById(R.id.durationTv);
                imageView = v.findViewById(R.id.imagee);
                parent = v.findViewById(R.id.parentLayout);

            }
        }

    }
}