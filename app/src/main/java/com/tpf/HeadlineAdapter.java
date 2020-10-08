package com.tpf;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class HeadlineAdapter extends RecyclerView.Adapter<HeadlineAdapter.HeadlineVH> {

    Activity activity;
List<HeadlineModel> list;

    public HeadlineAdapter(Activity activity, List<HeadlineModel> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public HeadlineVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.headline_item, null);
        return new HeadlineVH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadlineVH holder, int position) {
        final HeadlineModel model = list.get(position);

        holder.title.setText(model.getTitle());
        holder.author.setText("By - "+model.getAuthor());
        Picasso.get().load(model.getUrlToImage()).into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,DetailActivity.class);
                intent.putExtra("image",model.getUrlToImage());
                intent.putExtra("description",model.getDescription());
                intent.putExtra("content",model.getContent());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HeadlineVH extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView image;
        TextView author,title;
        public HeadlineVH(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            author = itemView.findViewById(R.id.author);
            title = itemView.findViewById(R.id.title);

            cardView = itemView.findViewById(R.id.card);
        }
    }
}
